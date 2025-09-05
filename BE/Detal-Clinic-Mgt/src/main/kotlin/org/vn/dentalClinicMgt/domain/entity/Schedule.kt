package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "schedule")
data class Schedule(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val scheduleId: Long = 0,

    @ManyToOne @JoinColumn(name = "patientId")
    val patient: Patient? = null,

    val date: LocalDate?,
    val status: String?,
    val booked: Boolean = false
)