package org.vn.dentalClinicMgt.controller.categoryService

import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.categoryService.CategoryServiceCreateInput
import org.vn.dentalClinicMgt.domain.dto.categoryService.CategoryServicePage
import org.vn.dentalClinicMgt.domain.dto.categoryService.CategoryServiceUpdateInput
import org.vn.dentalClinicMgt.domain.entity.CategoryService
import org.vn.dentalClinicMgt.service.CategoryServiceService

@Controller
class CategoryServiceQuery(
    private val categoryService: CategoryServiceService
){

    @PreAuthorize("hasAuthority('CATEGORYSERVICE_VIEW')")
    @QueryMapping
    fun getCategoryServicePage(
        @Argument page: Int,
        @Argument size: Int,
        @Argument search: String?,
    ) : CategoryServicePage {
        val pageable = PageRequest.of(page, size)
        val result = categoryService.listCategoryService(search, pageable)

        return CategoryServicePage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageNumber = result.number,
            pageSize = result.size
        )
    }

}

@Controller
class CategoryServiceMutation(
    private val categoryService: CategoryServiceService
){

    @PreAuthorize("hasAuthority('CATEGORYSERVICE_CREATE')")
    @MutationMapping
    fun createCategoryService(@Argument input: CategoryServiceCreateInput): CategoryService {
        return categoryService.createCategoryService(input)
    }

    @PreAuthorize("hasAuthority('CATEGORYSERVICE_UPDATE')")
    @MutationMapping
    fun updateCategoryService(@Argument input: CategoryServiceUpdateInput): CategoryService {
        return categoryService.updateCategoryService(input)
    }


}