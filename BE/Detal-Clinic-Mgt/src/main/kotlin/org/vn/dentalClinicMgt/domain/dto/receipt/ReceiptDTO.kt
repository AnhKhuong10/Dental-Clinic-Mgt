package org.vn.dentalClinicMgt.domain.dto.receipt

import org.vn.dentalClinicMgt.utils.constants.PaymentType
import java.time.LocalDate

data class ReceiptDTO(
    val receiptId: Long,
    val patientName: String,
    val patientPhone: String,
    val treatmentId: Long,
    val treatmentName: String,
    val price: Int,
    val discount: Int,
    val totalPrice: Int,
    val payment: Int,
    val date: LocalDate,
    val debit: Int,
    val paymentType: PaymentType,
)
