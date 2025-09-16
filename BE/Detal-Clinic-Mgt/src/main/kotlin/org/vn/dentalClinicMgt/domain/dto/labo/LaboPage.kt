package org.vn.dentalClinicMgt.domain.dto.labo

data class LaboPage(
    val content: List<LaboDTO>,
    val totalPages: Int,
    val totalElements: Long,
    val pageNumber: Int,
    val pageSize: Int,
)
