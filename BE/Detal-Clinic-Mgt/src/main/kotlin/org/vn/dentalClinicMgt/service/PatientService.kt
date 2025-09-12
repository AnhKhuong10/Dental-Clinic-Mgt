package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.patient.PatientCreateInput
import org.vn.dentalClinicMgt.domain.dto.patient.PatientDTO
import org.vn.dentalClinicMgt.domain.dto.patient.PatientUpdateInput

import org.vn.dentalClinicMgt.domain.entity.Patient
import org.vn.dentalClinicMgt.utils.constants.GenderEnum
import org.vn.dentalClinicMgt.utils.constants.PatientStatus

interface PatientService {

    fun listPatients(search : String?, gender: GenderEnum?, status: PatientStatus?, pageable: Pageable): Page<Patient>

    fun createPatient(input: PatientCreateInput): PatientDTO

    fun updatePatient(input: PatientUpdateInput): PatientDTO

    fun getPatientByPatientId(id: Long): PatientDTO?
}