package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "patient_records")
data class PatientRecord(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val patientRecordId: Long = 0,

    val reason: String?,
    val diagnostic: String?,
    val causal: String?,
    val date: LocalDate?,
    val treatment: String?,
    val marrowRecord: String?,
    val debit: Int?,
    val note: String?,
    val credit: Int?,
    val prescription: String?,

    @ManyToOne @JoinColumn(name = "userId")
    val user: User? = null
)
