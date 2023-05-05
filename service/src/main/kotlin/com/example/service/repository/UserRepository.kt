package com.example.service.repository

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID>

@Table(name = "users")
@Entity
@JsonPropertyOrder(value = ["firstName", "lastName", "email", "phone"])
class User(
    @Id
    @JsonIgnore
    val id: UUID = UUID.randomUUID(),
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
)
