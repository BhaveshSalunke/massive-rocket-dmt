package com.example.service.repository

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*

@Repository
interface StatRepository : JpaRepository<Stat, UUID> {
    fun findByProcessId(processId: UUID): List<Stat>
}

@Entity
@Table(name = "stats")
class Stat(
    @Id
    val id: UUID = UUID.randomUUID(),

    val processId: UUID,

    @JsonIgnore
    val batchId: UUID,

    var userCount: Long = 0,

    var duplicateCount: Long = 0,

    @Enumerated(value = EnumType.STRING)
    var processStatus: ProcessStatus = ProcessStatus.PROCESSING
) {
    enum class ProcessStatus { PROCESSING, COMPLETED }
}
