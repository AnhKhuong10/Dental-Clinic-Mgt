package org.vn.dentalClinicMgt.domain.dto.materialImport


data class MaterialImportPage(
    val content: List<MaterialImportDTO>,
    val totalPages: Int,
    val totalElements: Long,
    val pageSize: Int,
    val pageNumber: Int,
)