package org.vn.dentalClinicMgt.controller

import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.material.MaterialCreateInput
import org.vn.dentalClinicMgt.domain.dto.material.MaterialDTO
import org.vn.dentalClinicMgt.domain.dto.material.MaterialPage
import org.vn.dentalClinicMgt.domain.dto.material.MaterialUpdateInput
import org.vn.dentalClinicMgt.service.MaterialService

@Controller
class MaterialQuery (
    private val materialService: MaterialService
) {

    @QueryMapping
    fun getMaterialPage(
        @Argument page: Int,
        @Argument size: Int,
        @Argument search: String?,
    ): MaterialPage {

        val pageable = PageRequest.of(page, size)
        val result = materialService.getListMaterials(search, pageable)

        return MaterialPage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageSize = result.size,
            pageNumber = result.number,
        )
    }

    @QueryMapping
    fun getMaterialDetail(@Argument id: Long): MaterialDTO {
        return materialService.getMaterialDetails(id)
    }
}

@Controller
class MaterialMutation(
    private val materialService: MaterialService
){

    @MutationMapping
    fun createMaterial(@Argument input: MaterialCreateInput): MaterialDTO {
        return materialService.createMaterial(input)
    }

    @MutationMapping
    fun updateMaterial(@Argument input: MaterialUpdateInput): MaterialDTO {
        return materialService.updateMaterial(input)
    }
}