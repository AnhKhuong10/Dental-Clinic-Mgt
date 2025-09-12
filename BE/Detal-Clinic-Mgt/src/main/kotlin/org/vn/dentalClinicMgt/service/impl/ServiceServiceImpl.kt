package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.categoryService.CategoryServiceCreateInput
import org.vn.dentalClinicMgt.domain.dto.categoryService.CategoryServiceUpdateInput
import org.vn.dentalClinicMgt.domain.dto.service.ServiceCreateInput
import org.vn.dentalClinicMgt.domain.dto.service.ServiceDTO
import org.vn.dentalClinicMgt.domain.dto.service.ServicePage
import org.vn.dentalClinicMgt.domain.dto.service.ServiceUpdateInput
import org.vn.dentalClinicMgt.domain.entity.CategoryService
import org.vn.dentalClinicMgt.repository.CategoryServiceRepository
import org.vn.dentalClinicMgt.repository.ServiceRepository
import org.vn.dentalClinicMgt.service.ServiceService
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode

@Service
class ServiceServiceImpl (
    private val serviceRepository: ServiceRepository,
    private val categoryServiceRepository: CategoryServiceRepository,
) : ServiceService {
    override fun listServicePage(
        search: String?,
        minPrice: Int?,
        maxPrice: Int?,
        page: Pageable
    ): Page<org.vn.dentalClinicMgt.domain.entity.Service> {
        return serviceRepository.listServicePage(search, minPrice, maxPrice, page)
    }

    override fun createService(input: ServiceCreateInput): ServiceDTO {

        val category = categoryServiceRepository.findById(input.categoryServiceId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "CategoryService ${input.categoryServiceId} not found") }

        val service = org.vn.dentalClinicMgt.domain.entity.Service(
            serviceName = input.serviceName,
            price = input.price,
            marketPrice = input.marketPrice,
            categoryService = category
        )
        return serviceRepository.save(service).toDto()
    }

    override fun updateService(input: ServiceUpdateInput): ServiceDTO {

        val service = serviceRepository.findById(input.serviceId)
            .orElseThrow{ BusinessException(ErrorCode.NOT_FOUND, "Service ${input.serviceId} not found") }

        val category = categoryServiceRepository.findById(input.categoryServiceId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "CategoryService ${input.categoryServiceId} not found") }

        service.serviceName = input.serviceName
        service.price = input.price
        service.marketPrice = input.marketPrice
        service.categoryService =category

        return serviceRepository.save(service).toDto()

    }

    private fun org.vn.dentalClinicMgt.domain.entity.Service.toDto() = ServiceDTO (
        serviceId = this.serviceId,
        serviceName = this.serviceName,
        price = this.price,
        marketPrice = this.marketPrice,
        categoryServiceName = this.categoryService.categoryServiceName
    )



}