package com.example.service.controller

import com.example.service.repository.User
import com.example.service.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(@Autowired val userRepository: UserRepository) {

    @GetMapping("/users")
    fun getAllUsers() = userRepository.findAll()

    @PostMapping("/users")
    fun addUser(@RequestBody user: User) = userRepository.save(user)
}