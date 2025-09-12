package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.query.Param
import org.vn.dentalClinicMgt.domain.dto.service.ServiceCreateInput
import org.vn.dentalClinicMgt.domain.dto.service.ServiceDTO
import org.vn.dentalClinicMgt.domain.dto.service.ServiceUpdateInput

import org.vn.dentalClinicMgt.domain.entity.Service

interface ServiceService {

    fun listServicePage(@Param("search") search: String?, @Param("minPrice") minPrice: Int?,
                        @Param("maxPrice") maxPrice: Int?, page: Pageable): Page<Service>

    fun createService(input: ServiceCreateInput): ServiceDTO
    fun updateService(input: ServiceUpdateInput): ServiceDTO
}