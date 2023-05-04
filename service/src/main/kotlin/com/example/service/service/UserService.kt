package com.example.service.service

import com.example.service.repository.User
import com.example.service.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap

@Service
class UserService(@Autowired val userRepository: UserRepository) {

    private var duplicateUsers = mutableListOf<User>()

    fun addDuplicateUsers(user: User): Boolean {
        val existingUser = duplicateUsers.find{it.email == user.email}
        return if (existingUser == null) duplicateUsers.add(user) else false
    }

    fun getDuplicateUsersCount(): Int = duplicateUsers.size

    fun getDuplicateUsers(): MutableList<User> = duplicateUsers

    fun deleteAllDuplicateUsers() = duplicateUsers.clear()
    fun findAll(): MutableList<User> {
        return userRepository.findAll()
    }

    fun save(user: User) {
        if (userRepository.findUserByEmail(user.email) == null) {
            userRepository.save(user)
        }
        else {
            addDuplicateUsers(user)
        }
    }


    }
