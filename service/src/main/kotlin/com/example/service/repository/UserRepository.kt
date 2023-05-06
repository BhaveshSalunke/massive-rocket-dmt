package com.example.service.repository

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import jakarta.persistence.Column
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
    @Id @JsonIgnore val id: UUID = UUID.randomUUID(),

    val firstName: String = "",

    val lastName: String = "",

    @Column(unique = true) val email: String = "",

    @Column(unique = true) val phone: String = "",

    @JsonIgnore val processId: UUID
) {
    fun copy(
        id: UUID? = null,
        firstName: String? = null,
        lastName: String? = null,
        email: String? = null,
        phone: String? = null,
        processId: UUID? = null
    ) = User(
        id ?: this.id,
        firstName ?: this.firstName,
        lastName ?: this.lastName,
        email ?: this.email,
        phone ?: this.phone,
        processId ?: this.processId
    )
}
