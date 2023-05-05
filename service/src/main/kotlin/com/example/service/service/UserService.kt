package com.example.service.service

import com.example.service.repository.User
import com.example.service.repository.UserRepository
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvParser
import org.springframework.stereotype.Service
import java.nio.file.Path

const val BatchSize = 50000

@Service
class UserService(val userRepository: UserRepository) {
    private val mapper = CsvMapper().enable(CsvParser.Feature.SKIP_EMPTY_LINES)
    private val userReader = mapper.readerFor(User::class.java).with(mapper.schemaFor(User::class.java).withHeader())

    fun process(filePath: Path) {
        val reader = userReader.readValues<User>(filePath.toFile())

        val chunks = sequence {
            while (reader.hasNext()) {
                val chunk = mutableListOf<User>()
                repeat(BatchSize) {
                    chunk.add(reader.next())
                    if (!reader.hasNext()) return@repeat
                }
                yieldAll(listOf(chunk))
            }
        }

        chunks.forEach { processUsers(it) }

        filePath.toFile().delete()
    }

    fun processUsers(users: List<User>) {
        println("Processed ${users.size} users")
    }
}
