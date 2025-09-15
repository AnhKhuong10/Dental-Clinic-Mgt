package org.vn.dentalClinicMgt.domain.dto.patientRecordServiceMap

import org.vn.dentalClinicMgt.utils.constants.PatientRecordServiceStatus

data class PatientRecordServiceMapDTO(
    val serviceName: String,
    val servicePrice: Int,
    val status: PatientRecordServiceStatus,
)
