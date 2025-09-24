package org.vn.dentalClinicMgt.domain.dto.receipt

import org.vn.dentalClinicMgt.utils.constants.PaymentType

data class ReceiptCreateInput(
    val treatmentId: Long,
    val patientRecordId: Long,
    val paymentType: PaymentType,
    val payment: Int
)
