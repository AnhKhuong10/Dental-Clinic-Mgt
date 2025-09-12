package org.vn.dentalClinicMgt.domain.dto.service

data class ServiceDTO(
    val serviceId: Long,
    val serviceName: String,
    val price: Int,
    val marketPrice: Int?,
    val categoryServiceName: String
)
