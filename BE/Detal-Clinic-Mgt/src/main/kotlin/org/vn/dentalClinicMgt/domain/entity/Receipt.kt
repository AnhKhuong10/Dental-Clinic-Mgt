package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "receipts")
data class Receipt(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val receiptId: Long = 0,

    @ManyToOne @JoinColumn(name = "patientId")
    val patient: Patient? = null,

    val paymentDate: LocalDate?,
    val debit: Int?,
    val credit: Int?,
    val status: String?
)