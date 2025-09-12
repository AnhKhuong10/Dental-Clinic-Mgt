package org.vn.dentalClinicMgt.domain.dto.categoryService

import org.vn.dentalClinicMgt.domain.entity.CategoryService

data class CategoryServicePage(
    val content: List<CategoryService>,
    val totalPages: Int,
    val totalElements: Long,
    val pageNumber: Int,
    val pageSize: Int,
)
