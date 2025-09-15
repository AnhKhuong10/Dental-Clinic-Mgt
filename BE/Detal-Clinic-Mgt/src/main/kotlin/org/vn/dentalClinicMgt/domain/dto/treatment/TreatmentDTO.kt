package org.vn.dentalClinicMgt.domain.dto.treatment


data class TreatmentDTO(
    val id: Long,
    val patientId: Long,
    val services: List<TreatmentServiceMapDTO>
)