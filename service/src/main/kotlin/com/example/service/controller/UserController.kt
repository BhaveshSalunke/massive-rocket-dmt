package com.example.service.controller

import com.example.service.repository.User
import com.example.service.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(@Autowired val userRepository: UserRepository) {

    @GetMapping("/users")
    fun getAllUsers() = try {
        val existingUsers = userRepository.findAll()
        if (existingUsers.isEmpty()) ResponseEntity(HttpStatus.NO_CONTENT)
        else ResponseEntity.status(HttpStatus.OK).body(existingUsers)
    } catch (e: Exception) {
        ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @PostMapping("/users")
    fun addUser(@RequestBody user: User) = try {
        ResponseEntity(userRepository.save(user), HttpStatus.CREATED)
    } catch (e :Exception) {
        ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}