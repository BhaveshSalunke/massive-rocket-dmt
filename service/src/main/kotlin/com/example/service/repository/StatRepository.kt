package com.example.service.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.UUID


@Repository
interface StatRepository: JpaRepository<Stat, UUID> {

}

@Entity
@Table(name = "stats")
class Stat(
    @Id
    val id: UUID = UUID.randomUUID(),
    var userCount: Long = 0,
    var duplicateCount: Long = 0
)


@Service
class StatService(
    @Autowired val statRepository: StatRepository) {

    fun getAllStats(): MutableList<Stat> {
        return statRepository.findAll()
    }
}
