package org.vn.dentalClinicMgt.domain.dto.categoryService

import jakarta.validation.constraints.NotBlank

data class CategoryServiceUpdateInput(

    val categoryServiceId : Long,

    @field:NotBlank(message = "Category name is required")
    val categoryServiceName : String,
)
