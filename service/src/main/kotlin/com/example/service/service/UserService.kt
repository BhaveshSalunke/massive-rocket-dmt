package com.example.service.service

import com.example.service.repository.Stat
import com.example.service.repository.StatRepository
import com.example.service.repository.User
import com.example.service.repository.UserRepository
import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import jakarta.transaction.Transactional
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.util.*

const val BatchSize = 100000

@Service
class UserService(
    private val userRepository: UserRepository,
    private val statRepository: StatRepository,
    dispatchers: CoroutineDispatcher = Dispatchers.Default
) {
    private val scope = CoroutineScope(dispatchers)
    private val mapper = CsvMapper().enable(CsvParser.Feature.SKIP_EMPTY_LINES)
    private val userReader = mapper.readerFor(User::class.java).with(mapper.schemaFor(User::class.java).withHeader())

    fun process(filePath: Path, processId: UUID) = scope.launch {
        val reader = userReader.readValues<User>(filePath.toFile())
        readUserChunks(reader).forEach { processUsers(it, processId) }
        filePath.toFile().delete()
    }

    private fun readUserChunks(reader: MappingIterator<User>) = sequence {
        while (reader.hasNext()) {
            val chunk = mutableListOf<User>()
            for (i in 0 until BatchSize) {
                chunk.add(reader.next())
                if (!reader.hasNext()) break
            }
            yieldAll(listOf(chunk))
        }
    }

    @Transactional
    fun processUsers(users: List<User>, processId: UUID) = scope.launch {
        val batchId = UUID.randomUUID()
        val stat = statRepository.save(Stat(processId = processId, batchId = batchId))
        users.distinctBy { it.email }.distinctBy { it.phone }
            .map { it.copy(id = UUID.randomUUID(), processId = processId) }
            .forEach { it.save(stat) }
        stat.userCount = users.size.toLong()
        stat.processStatus = Stat.ProcessStatus.COMPLETED
        statRepository.save(stat)
    }

    private fun User.save(stat: Stat) = try {
        userRepository.save(this)
    } catch (e: DataIntegrityViolationException) {
        stat.duplicateCount += 1
    }

}
