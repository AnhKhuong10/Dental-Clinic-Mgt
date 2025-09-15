package org.vn.dentalClinicMgt.domain.dto.patientRecord


data class PatientRecordPage(
    val content: List<PatientRecordDTO>,
    val totalPages: Int,
    val totalElements: Long,
    val pageNumber: Int,
    val pageSize: Int
)
