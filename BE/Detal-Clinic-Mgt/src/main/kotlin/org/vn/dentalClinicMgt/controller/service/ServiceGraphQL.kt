package org.vn.dentalClinicMgt.controller.service

import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.service.ServiceCreateInput
import org.vn.dentalClinicMgt.domain.dto.service.ServiceDTO
import org.vn.dentalClinicMgt.domain.dto.service.ServicePage
import org.vn.dentalClinicMgt.domain.dto.service.ServiceUpdateInput
import org.vn.dentalClinicMgt.domain.entity.Service
import org.vn.dentalClinicMgt.service.ServiceService
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode

@Controller
class ServiceQuery(
    private val service: ServiceService
){
    @PreAuthorize("hasAuthority('SERVICE_VIEW')")
    @QueryMapping
    fun getServicePage(
        @Argument page: Int,
        @Argument size: Int,
        @Argument search: String?,
        @Argument minPrice: Int?,
        @Argument maxPrice: Int?,
    ): ServicePage{

        // Validate minPrice và maxPrice
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw BusinessException(ErrorCode.BAD_REQUEST, "MinPrice must less than MaxPrice")
        }

        val pageable = PageRequest.of(page, size)
        val result = service.listServicePage(search, minPrice, maxPrice, pageable)

        return ServicePage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageNumber = result.number,
            pageSize = result.size
        )
    }
}

@Controller
class ServiceMutation(
    private val service: ServiceService,
){

    @PreAuthorize("hasAuthority('SERVICE_CREATE')")
    @MutationMapping
    fun createService(@Argument @Valid input: ServiceCreateInput): ServiceDTO {
        return service.createService(input)
    }

    @PreAuthorize("hasAuthority('SERVICE_UPDATE')")
    @MutationMapping
    fun updateService(@Argument @Valid input: ServiceUpdateInput): ServiceDTO {
        return service.updateService(input)
    }
}
