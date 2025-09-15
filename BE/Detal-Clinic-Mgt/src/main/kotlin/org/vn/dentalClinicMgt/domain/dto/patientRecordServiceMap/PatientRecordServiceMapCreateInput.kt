package org.vn.dentalClinicMgt.domain.dto.patientRecordServiceMap

import org.vn.dentalClinicMgt.utils.constants.PatientRecordServiceStatus

data class PatientRecordServiceMapCreateInput(
    val patientRecordId: Long,
    val serviceIds: List<Long>,
)