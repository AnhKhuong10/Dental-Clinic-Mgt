package org.vn.dentalClinicMgt.domain.dto.patientRecordServiceMap

data class PatientRecordServiceMapPage(
    val content: List<PatientRecordServiceMapDTO>,
    val totalPages: Int,
    val totalElements: Long,
    val pageNumber: Int,
    val pageSize: Int,
)
