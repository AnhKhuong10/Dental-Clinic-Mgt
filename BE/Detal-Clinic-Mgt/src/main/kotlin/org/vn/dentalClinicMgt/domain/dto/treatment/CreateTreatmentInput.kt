package org.vn.dentalClinicMgt.domain.dto.treatment

data class CreateTreatmentInput(
    val patientId: Long,
    val serviceIds: List<Long>
)


