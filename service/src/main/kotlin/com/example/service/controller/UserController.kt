package com.example.service.controller

import com.example.service.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@RestController
@CrossOrigin(origins = ["http://localhost:3000/"])
class UserController(@Autowired val userService: UserService) {

    @PostMapping("/users/dataset")
    fun getAllUsers(@RequestParam("file") file: MultipartFile) {
        val fileDir = Paths.get(".out").toAbsolutePath().toFile()
        if (!fileDir.exists()) fileDir.mkdirs()
        val filePath = Files.write(Paths.get("${fileDir.path}/${UUID.randomUUID()}.csv"), file.bytes)
        userService.process(filePath)
    }

}
