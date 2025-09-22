package org.vn.dentalClinicMgt.domain.dto.materialImport

import org.vn.dentalClinicMgt.domain.dto.material.MaterialDTO

data class MaterialImportPage(
    val content: List<MaterialDTO>,
    val totalPages: Int,
    val totalElements: Long,
    val pageSize: Int,
    val pageNumber: Int,
)