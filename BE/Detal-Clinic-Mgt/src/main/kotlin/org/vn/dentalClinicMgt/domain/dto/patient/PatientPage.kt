package org.vn.dentalClinicMgt.domain.dto.patient

import org.vn.dentalClinicMgt.domain.entity.Patient

data class PatientPage(
    val content: List<Patient>,
    val totalPages: Int,
    val totalElements: Long,
    val pageNumber: Int,
    val pageSize: Int
)
