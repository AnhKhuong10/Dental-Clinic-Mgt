package org.vn.dentalClinicMgt.domain.dto.material

import org.vn.dentalClinicMgt.domain.entity.Material

data class MaterialPage(
    val content: List<MaterialDTO>,
    val totalPages: Int,
    val totalElements: Long,
    val pageSize: Int,
    val pageNumber: Int,
)
