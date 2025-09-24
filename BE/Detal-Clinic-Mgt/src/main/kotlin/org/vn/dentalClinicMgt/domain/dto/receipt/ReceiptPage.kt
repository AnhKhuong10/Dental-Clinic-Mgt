package org.vn.dentalClinicMgt.domain.dto.receipt

data class ReceiptPage(
    val content: List<ReceiptDTO>,
    val totalPages: Int,
    val totalElements: Long,
    val pageSize: Int,
    val pageNumber: Int,
)
