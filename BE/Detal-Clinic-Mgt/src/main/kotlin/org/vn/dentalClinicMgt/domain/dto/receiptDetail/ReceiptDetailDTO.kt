package org.vn.dentalClinicMgt.domain.dto.receiptDetail

data class ReceiptDetailDTO(
    val receiptDetailId: Long,
    val receiptId: Long,
    val itemType: String,
    val itemName: String,
    val quantity: Int,
    val unitPrice: Int,
    val totalPrice: Int
)
