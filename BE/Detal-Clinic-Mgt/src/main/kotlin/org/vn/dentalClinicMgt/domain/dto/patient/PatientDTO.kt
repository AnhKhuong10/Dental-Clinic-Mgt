package org.vn.dentalClinicMgt.domain.dto.patient

import org.vn.dentalClinicMgt.utils.constants.GenderEnum
import org.vn.dentalClinicMgt.utils.constants.PatientStatus
import java.time.LocalDate

data class PatientDTO(
    val patientId: Long,
    val patientName: String,
    val birthdate: LocalDate?,
    val gender: GenderEnum?,
    val address: String?,
    val phone: String?,
    val email: String?,
    val bodyPrehistory: String?,
    val teethPrehistory: String?,
    val status: PatientStatus,
    val isDeleted: Boolean
)