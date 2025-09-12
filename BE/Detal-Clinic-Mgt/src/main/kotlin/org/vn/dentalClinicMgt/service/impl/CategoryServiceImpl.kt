package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.categoryService.CategoryServiceCreateInput
import org.vn.dentalClinicMgt.domain.dto.categoryService.CategoryServiceUpdateInput
import org.vn.dentalClinicMgt.domain.entity.CategoryService
import org.vn.dentalClinicMgt.repository.CategoryServiceRepository
import org.vn.dentalClinicMgt.service.CategoryServiceService
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode

@Service
class CategoryServiceImpl (
    private val categoryServiceRepository: CategoryServiceRepository
) : CategoryServiceService {
    override fun listCategoryService(search: String?, pageable: Pageable): Page<CategoryService> {
        return categoryServiceRepository.listCategoryService(search, pageable)
    }

    override fun createCategoryService(input: CategoryServiceCreateInput): CategoryService {

        val category = CategoryService(
            categoryServiceName = input.categoryServiceName,
        )
        return categoryServiceRepository.save(category)
    }

    override fun updateCategoryService(input: CategoryServiceUpdateInput): CategoryService {
         val category = categoryServiceRepository.findById(input.categoryServiceId)
             .orElseThrow{ BusinessException(ErrorCode.NOT_FOUND, "Category service ${input.categoryServiceId} not found") }

        category.categoryServiceName = input.categoryServiceName
        return categoryServiceRepository.save(category)
    }
}