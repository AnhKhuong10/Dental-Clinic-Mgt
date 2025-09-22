package org.vn.dentalClinicMgt.domain.dto.materialExport

import org.vn.dentalClinicMgt.domain.entity.MaterialExport

data class MaterialExportPage(
    val content: List<MaterialExportDTO>,
    val totalPages: Int,
    val totalElements: Long,
    val pageSize: Int,
    val pageNumber: Int,
)
