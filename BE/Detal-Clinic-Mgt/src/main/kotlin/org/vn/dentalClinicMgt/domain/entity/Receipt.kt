package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import org.vn.dentalClinicMgt.utils.constants.PaymentType
import java.time.LocalDate

@Entity
@Table(name = "receipts")
data class Receipt(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val receiptId: Long = 0,

    @ManyToOne @JoinColumn(name = "treatmentId")
    val treatment: Treatment,
    @ManyToOne @JoinColumn(name = "patientRecordId", nullable = true)
    val patientRecord: PatientRecord? = null,
    val price: Int,
    val discount: Int,
    val totalPrice: Int,
    val payment: Int,
    val date: LocalDate,
    val debit: Int,
    @Enumerated(EnumType.STRING)
    val paymentType: PaymentType,
)