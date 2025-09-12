package org.vn.dentalClinicMgt.domain.dto.service

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank


data class ServiceCreateInput(

    @field:NotBlank(message = "serviceName must not be blank")
    val serviceName: String,

    @field:Min(value = 0, message = "price must positive number")
    val price: Int,

    @field:Min(value = 0, message = "price must positive number")
    val marketPrice: Int,

    val categoryServiceId: Long
)
