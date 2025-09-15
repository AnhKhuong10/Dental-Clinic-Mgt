package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.patientRecordServiceMap.PatientRecordServiceMapCreateInput
import org.vn.dentalClinicMgt.domain.dto.patientRecordServiceMap.PatientRecordServiceMapDTO

interface PatientRecordServiceMapService {

    fun listPatientRecordService(pageable: Pageable): Page<PatientRecordServiceMapDTO>

    fun createPatientRecordService(input: PatientRecordServiceMapCreateInput): List<PatientRecordServiceMapDTO>

    fun makeServiceAsInProgress(id: Long): PatientRecordServiceMapDTO

    fun makeServiceAsDone(id: Long): PatientRecordServiceMapDTO

    fun makeServiceAsCancelled(id: Long): PatientRecordServiceMapDTO

}