package org.vn.dentalClinicMgt.domain.dto.specimen


data class SpecimenPage(
    val content: List<SpecimenDTO>,
    val totalPages: Int,
    val totalElements: Long,
    val pageSize: Int,
    val pageNumber: Int,
)
