package org.vn.dentalClinicMgt.domain.dto.categoryService

import jakarta.validation.constraints.NotBlank

data class CategoryServiceCreateInput(

    @field:NotBlank(message = "Category name is required")
    val categoryServiceName: String,
)
