package com.example.service.service

import com.example.service.repository.User
import com.example.service.repository.UserRepository
import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import java.nio.file.Path

const val BatchSize = 100000

@Service
class UserService(val userRepository: UserRepository) {
    private val mapper = CsvMapper().enable(CsvParser.Feature.SKIP_EMPTY_LINES)
    private val userReader = mapper.readerFor(User::class.java).with(mapper.schemaFor(User::class.java).withHeader())
    private val scope = CoroutineScope(Dispatchers.Default)

    fun process(filePath: Path) = scope.launch {
        val reader = userReader.readValues<User>(filePath.toFile())
        readUserChunks(reader).forEach { scope.launch { processUsers(it) } }
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

    private fun processUsers(users: List<User>) {
        println("Processed ${users.size} users")
    }
}
