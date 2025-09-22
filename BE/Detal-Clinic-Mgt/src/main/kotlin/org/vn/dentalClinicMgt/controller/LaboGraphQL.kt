package org.vn.dentalClinicMgt.controller

import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.labo.LaboCreateInput
import org.vn.dentalClinicMgt.domain.dto.labo.LaboDTO
import org.vn.dentalClinicMgt.domain.dto.labo.LaboPage
import org.vn.dentalClinicMgt.domain.dto.labo.LaboUpdateInput
import org.vn.dentalClinicMgt.service.LaboService

@Controller
class LaboQuery(
    private val laboService: LaboService
){
    @PreAuthorize("hasAuthority('LABO_VIEW')")
    @QueryMapping
    fun getLaboPage(
        @Argument page: Int,
        @Argument size: Int,
        @Argument search: String?,
    ): LaboPage{
        val pageable = PageRequest.of(page, size)
        val result = laboService.getListLabo(search, pageable)

        return LaboPage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageNumber = result.number,
            pageSize = result.size
        )
    }

    @PreAuthorize("hasAuthority('LABO_DETAIL')")
    @QueryMapping
    fun getLaboDetail(
        @Argument id: Long
    ): LaboDTO{

        return laboService.getLaboById(id)
    }
}

@Controller
class LaboMutation(
    private val laboService: LaboService
){

    @PreAuthorize("hasAuthority('LABO_CREATE')")
    @MutationMapping
    fun createLabo(@Argument input: LaboCreateInput): LaboDTO{
        return laboService.createLabo(input)
    }

    @PreAuthorize("hasAuthority('LABO_UPDATE')")
    @MutationMapping
    fun updateLabo(@Argument input: LaboUpdateInput): LaboDTO{
        return laboService.updateLabo(input)
    }
}