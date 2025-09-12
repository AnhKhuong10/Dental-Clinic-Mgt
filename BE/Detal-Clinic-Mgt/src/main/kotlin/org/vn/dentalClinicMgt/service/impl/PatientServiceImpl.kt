package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.patient.PatientCreateInput
import org.vn.dentalClinicMgt.domain.dto.patient.PatientDTO
import org.vn.dentalClinicMgt.domain.dto.patient.PatientUpdateInput
import org.vn.dentalClinicMgt.domain.entity.Patient
import org.vn.dentalClinicMgt.repository.PatientRepository
import org.vn.dentalClinicMgt.service.PatientService
import org.vn.dentalClinicMgt.utils.constants.GenderEnum
import org.vn.dentalClinicMgt.utils.constants.PatientStatus
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode
import java.time.LocalDate

@Service
class PatientServiceImpl (
    private val patientRepository: PatientRepository
) : PatientService{
    override fun listPatients(search: String?, gender: GenderEnum?, status: PatientStatus?, pageable: Pageable): Page<Patient> {
        return patientRepository.listPatients(search, gender, status, pageable)
    }

    override fun createPatient(input: PatientCreateInput): PatientDTO {

        val patient = Patient(
            patientName = input.patientName,
            birthdate = input.birthdate,
            gender = input.gender,
            address = input.address,
            phone = input.phone,
            email = input.email,
            bodyPrehistory = input.bodyPrehistory,
            teethPrehistory = input.teethPrehistory,
            status = PatientStatus.NOT_TREATMENT
        )
        return patientRepository.save(patient).toDTO()
    }

    override fun updatePatient(input: PatientUpdateInput): PatientDTO {
        val patient = patientRepository.findById(input.patientId)
            .orElseThrow{ BusinessException(ErrorCode.NOT_FOUND,"Patient not found")}

        patient.patientName = input.patientName
        patient.gender = input.gender
        patient.birthdate = input.birthdate
        patient.gender = GenderEnum.valueOf(input.gender.name)
        patient.address = input.address
        patient.phone = input.phone
        patient.email = input.email
        patient.bodyPrehistory = input.bodyPrehistory
        patient.teethPrehistory = input.teethPrehistory
        patient.status = PatientStatus.valueOf(input.status.name)
       return  patientRepository.save(patient).toDTO()
    }

    override fun getPatientByPatientId(id: Long): PatientDTO? {
        val patient = patientRepository.findById(id).orElseThrow{
            BusinessException(ErrorCode.NOT_FOUND,"Patient Not found")
        }

        return patient.toDTO()
    }

    private fun Patient.toDTO() = PatientDTO (
        patientId = this.patientId,
        patientName = this.patientName,
        birthdate = this.birthdate?: LocalDate.now(),
        gender = this.gender,
        address = this.address,
        phone = this.phone,
        email = this.email,
        bodyPrehistory = this.bodyPrehistory,
        teethPrehistory = this.teethPrehistory,
        status = this.status,
        isDeleted = this.isDeleted,
    )

}