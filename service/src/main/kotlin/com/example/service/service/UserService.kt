package com.example.service.service

import com.example.service.repository.Stat
import com.example.service.repository.StatRepository
import com.example.service.repository.User
import com.example.service.repository.UserRepository
import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.util.*
import kotlin.io.path.name

const val BatchSize = 5

@Service
class UserService(
    private val userRepository: UserRepository,
    private val statRepository: StatRepository,
    dispatchers: CoroutineDispatcher = Dispatchers.Default
) {
    private val scope = CoroutineScope(dispatchers)
    private val mapper = CsvMapper().enable(CsvParser.Feature.SKIP_EMPTY_LINES).enable(CsvParser.Feature.TRIM_SPACES)
    private val userReader = mapper.readerFor(User::class.java).with(mapper.schemaFor(User::class.java))

    fun process(filePath: Path) = scope.launch {
        statRepository.deleteAll()
        val reader = userReader.readValues<User>(filePath.toFile())
        val stats = Stat(id = UUID.fromString(filePath.fileName.name.removeSuffix(".csv")))
        readUserChunks(reader).forEach { processUsers(it, stats) }
        filePath.toFile().delete()
        statRepository.save(stats)
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

    private fun processUsers(users: List<User>, stat: Stat) {
        var count = 0
        users.forEach {
            try {
                userRepository.save(it)
            } catch (_: DataIntegrityViolationException) {
                count += 1
            }
        }
        stat.userCount += users.size
        stat.duplicateCount += count
    }

}
