package com.example.service.controller

import com.example.service.repository.User
import com.example.service.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:3000/"])
class UserController(@Autowired val userService: UserService) {

    @GetMapping("/users")
    fun getAllUsers() = try {
        val existingUsers = userService.findAll()
        ResponseEntity.status(HttpStatus.OK).body(existingUsers)
    } catch (e: Exception) {
        ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @PostMapping("/users")
    @CrossOrigin(origins = ["http://localhost:3000/"])
    fun addUser(@RequestBody user: User) = try {
        ResponseEntity(userService.save(user), HttpStatus.CREATED)
    } catch (e : Exception) {
        ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
    }


    @GetMapping("/duplicate-count")
    fun getDuplicateCount(): Int {
        return userService.getDuplicateUsersCount()
    }

    @GetMapping("/duplicate-users")
    fun getRepeatUsers(): MutableList<User> {
        return userService.getDuplicateUsers()
    }

    @DeleteMapping("/duplicate-count")
    fun deleteDuplicateCount() {
        userService.deleteAllDuplicateUsers()
    }

}
