package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.categoryService.CategoryServiceCreateInput
import org.vn.dentalClinicMgt.domain.dto.categoryService.CategoryServiceUpdateInput
import org.vn.dentalClinicMgt.domain.entity.CategoryService

interface CategoryServiceService {
    fun listCategoryService(search: String?, pageable: Pageable): Page<CategoryService>

    fun createCategoryService(input: CategoryServiceCreateInput): CategoryService

    fun updateCategoryService(input: CategoryServiceUpdateInput): CategoryService
}