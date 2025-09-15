package org.vn.dentalClinicMgt.domain.dto.treatment

data class TreatmentServiceMapDTO(
    val id: Long,
    val serviceName: String,
    val price: Int,
    val discount: Int,
)
