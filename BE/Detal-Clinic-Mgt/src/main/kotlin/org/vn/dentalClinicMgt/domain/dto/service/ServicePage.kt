package org.vn.dentalClinicMgt.domain.dto.service

import org.vn.dentalClinicMgt.domain.entity.Service

data class ServicePage(
    val content: List<Service>,
    val totalPages: Int,
    val totalElements: Long,
    val pageSize: Int,
    val pageNumber: Int,
)
