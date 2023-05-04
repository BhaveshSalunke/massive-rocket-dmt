package com.example.service.controller

import com.example.service.repository.User
import com.example.service.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(@Autowired val userRepository: UserRepository) {
    var duplicate : Long = 0
    @GetMapping("/users")
    @CrossOrigin(origins = ["http://localhost:3000/"])
    fun getAllUsers() = try {
        val existingUsers = userRepository.findAll()
        ResponseEntity.status(HttpStatus.OK).body(existingUsers)
    } catch (e: Exception) {
        ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @PostMapping("/users")
    @CrossOrigin(origins = ["http://localhost:3000/"])
    fun addUser(@RequestBody user: User) = try {
        val isUser = userRepository.existsByEmail(user.email!!)
        if (!isUser)
        ResponseEntity(userRepository.save(user), HttpStatus.CREATED)
        else this.duplicate += 1
    } catch (e :Exception) {
        ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @DeleteMapping("/delete-all")
    fun deleteAll(): String {
        userRepository.deleteAll()
        return "All user entries deleted"
    }

    @GetMapping("/duplicate-count")
    fun getDuplicateCount(): Long {
        return duplicate
    }
    @DeleteMapping("/duplicate-count")
    fun deleteDuplicateCount() {
        this.duplicate = 0
    }

}
