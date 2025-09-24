package org.vn.dentalClinicMgt.domain.dto.receipt

data class ReceiptUpdateInput(
    val receiptId: Long,
    val treatmentId: Long,
)
