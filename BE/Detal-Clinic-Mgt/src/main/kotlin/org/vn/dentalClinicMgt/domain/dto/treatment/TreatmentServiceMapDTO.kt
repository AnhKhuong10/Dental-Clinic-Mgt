package org.vn.dentalClinicMgt.domain.dto.treatment

data class TreatmentServiceMapDTO(
    val treatmentServiceMapId: Long,
    val serviceName: String,
    val price: Int,
    val discount: Int,
)
