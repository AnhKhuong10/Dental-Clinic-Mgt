package org.vn.dentalClinicMgt.domain.dto.patientRecordServiceMap

import org.vn.dentalClinicMgt.utils.constants.PatientRecordServiceStatus

data class PatientRecordServiceMapUpdateInput(
    val patientRecordId: Long,
    val serviceIds: List<Long>,
    val status: PatientRecordServiceStatus
)
