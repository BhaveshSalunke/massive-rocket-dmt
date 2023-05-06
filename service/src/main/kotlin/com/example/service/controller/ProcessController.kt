package com.example.service.controller

import com.example.service.service.CsvGenerator
import com.example.service.service.StatService
import com.example.service.service.UserService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@RestController
@CrossOrigin(origins = ["http://localhost:3000/"])
class ProcessController(
    val userService: UserService,
    val statService: StatService,
    val csvGenerator: CsvGenerator
) {

    @PostMapping("/process/dataset")
    fun getAllUsers(@RequestParam("file") file: MultipartFile): Map<String, UUID> {
        val fileDir = Paths.get(".out").toAbsolutePath().toFile()
        if (!fileDir.exists()) fileDir.mkdirs()
        val processId = UUID.randomUUID()
        val filePath = Files.write(Paths.get("${fileDir.path}/$processId.csv"), file.bytes)
        userService.process(filePath, processId)
        return mapOf("processId" to processId)
    }

    @GetMapping("/process/{processId}/stats")
    fun getStats(@PathVariable processId: UUID) = statService.getStats(processId)

    @GetMapping("/csv/{recordsCount}")
    fun generateCsv(@PathVariable recordsCount: Long): String {
        val file = csvGenerator.generateCsvFile(recordsCount)
        return file.absolutePath
    }
}
