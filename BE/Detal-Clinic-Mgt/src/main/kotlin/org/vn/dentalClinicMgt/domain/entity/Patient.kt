package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "patients")
data class Patient(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val patientId: Long = 0,

    @Column(nullable = false)
    val patientName: String,

    val birthdate: LocalDate?,
    val gender: Boolean?,
    val address: String?,
    val phone: String?,
    val email: String?,
    val bodyPrehistory: String?,
    val teethPrehistory: String?,
    val status: Int = 1,
    val isDeleted: Boolean = false
)
