package com.example.service.service

import com.example.service.repository.Stat
import com.example.service.repository.Stat.ProcessStatus.COMPLETED
import com.example.service.repository.Stat.ProcessStatus.PROCESSING
import com.example.service.repository.StatRepository
import com.github.javafaker.Faker
import com.opencsv.CSVWriter
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter
import java.util.*

@Service
class StatService(val statRepository: StatRepository) {
    fun getStats(processId: UUID) = statRepository.findByProcessId(processId).reduce { a, b ->
        Stat(
            a.id,
            a.processId,
            a.batchId,
            a.userCount + b.userCount,
            a.duplicateCount + b.duplicateCount,
            if (a.processStatus == PROCESSING || b.processStatus == PROCESSING) PROCESSING else COMPLETED
        )
    }
}


@Service
class CsvGenerator {
    private val faker = Faker()

    fun generateCsvFile(recordCount: Long): File {
        val file = File("records.csv")
        val writer = CSVWriter(FileWriter(file))
        val header = arrayOf("firstname", "lastname", "email", "phone")
        writer.writeNext(header)

        for (i in 1..recordCount) {
            val firstName = faker.name().firstName()
            val lastName = faker.name().lastName()
            val email = "$firstName.$lastName@${faker.internet().domainName()}"
            val phone = generateRandomPhone()
            val record = arrayOf(firstName, lastName, email, phone)
            writer.writeNext(record)
        }

        writer.close()
        return file
    }

    private fun generateRandomPhone(): String {
        val random = Random()
        val digits = (1..10).map { random.nextInt(10) }.joinToString("")
        return "(${digits.substring(0, 3)}) ${digits.substring(3, 6)}-${digits.substring(6)}"
    }

}
