package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

import org.vn.dentalClinicMgt.domain.dto.patientRecord.PatientRecordCreateInput
import org.vn.dentalClinicMgt.domain.dto.patientRecord.PatientRecordDTO
import org.vn.dentalClinicMgt.domain.dto.patientRecord.PatientRecordUpdateInput
import org.vn.dentalClinicMgt.domain.entity.PatientRecord
import java.time.LocalDate

interface PatientRecordService {

    fun listPatientRecords(search: String?, fromDate: LocalDate?, toDate: LocalDate?, pageable: Pageable): Page<PatientRecordDTO>

    fun createPatientRecord(input: PatientRecordCreateInput): PatientRecordDTO

    fun updatePatientRecord(input: PatientRecordUpdateInput): PatientRecordDTO
}