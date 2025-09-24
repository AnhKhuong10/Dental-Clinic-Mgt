package org.vn.dentalClinicMgt.domain.dto.receiptDetail

data class ReceiptDetailPage(
    val content: List<ReceiptDetailDTO>,
    val totalPages: Int,
    val totalElements: Long,
    val pageNumber: Int,
    val pageSize: Int,
)
