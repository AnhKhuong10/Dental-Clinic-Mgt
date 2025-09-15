package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.patientRecord.PatientRecordCreateInput
import org.vn.dentalClinicMgt.domain.dto.patientRecord.PatientRecordDTO
import org.vn.dentalClinicMgt.domain.dto.patientRecord.PatientRecordUpdateInput
import org.vn.dentalClinicMgt.domain.entity.PatientRecord
import org.vn.dentalClinicMgt.repository.PatientRecordRepository
import org.vn.dentalClinicMgt.repository.TreatmentRepository
import org.vn.dentalClinicMgt.repository.UserRepository
import org.vn.dentalClinicMgt.service.PatientRecordService
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode
import java.time.LocalDate

@Service
class PatientRecordServiceImpl (
    private val patientRecordRepository: PatientRecordRepository,
    private val userRepository: UserRepository,
    private val treatmentRepository: TreatmentRepository
) : PatientRecordService {
    override fun listPatientRecords(
        search: String?,
        fromDate: LocalDate?,
        toDate: LocalDate?,
        pageable: Pageable
    ): Page<PatientRecordDTO> {
        val patientRecordDto = patientRecordRepository.listPatientRecords(search, fromDate, toDate, pageable)
        return patientRecordDto.map { it.toDTO() }
    }

    override fun createPatientRecord(input: PatientRecordCreateInput): PatientRecordDTO {
        val doctor = userRepository.findById(input.userId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND,"User ${input.userId} not found") }

        if (doctor.role.roleName != "DOCTOR") {
            throw BusinessException(ErrorCode.FORBIDDEN, "User ${input.userId} is not a doctor")
        }

        val treatment = treatmentRepository.findById(input.treatmentId)
        .orElseThrow { BusinessException( ErrorCode.NOT_FOUND ,"Treatment ${input.treatmentId} not found") }

        val record = patientRecordRepository.save(
            PatientRecord(
                reason = input.reason,
                diagnostic = input.diagnostic,
                causal = input.causal,
                date = input.date,
                treatmentDescription = input.treatmentDescription,
                marrowRecord = input.marrowRecord,
                debit = input.debit,
                note = input.note,
                prescription = input.prescription,
                treatment = treatment,
                user = doctor
            )
        )
        return record.toDTO()

    }

    override fun updatePatientRecord(input: PatientRecordUpdateInput): PatientRecordDTO {
        val patientRecord = patientRecordRepository.findById(input.patientRecordId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "PatientRecord with id ${input.patientRecordId} not found") }

        val doctor = userRepository.findById(input.userId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND,"User ${input.userId} not found") }

        if (doctor.role.roleName != "DOCTOR") {
            throw BusinessException(ErrorCode.FORBIDDEN, "User ${input.userId} is not a doctor")
        }

        val treatment = treatmentRepository.findById(input.treatmentId)
            .orElseThrow { BusinessException( ErrorCode.NOT_FOUND ,"Treatment ${input.treatmentId} not found") }

        patientRecord.reason = input.reason
        patientRecord.diagnostic = input.diagnostic
        patientRecord.causal = input.causal
        patientRecord.date = input.date
        patientRecord.treatmentDescription = input.treatmentDescription
        patientRecord.marrowRecord = input.marrowRecord
        patientRecord.debit = input.debit
        patientRecord.note = input.note
        patientRecord.treatment = treatment
        patientRecord.user = doctor
        patientRecord.prescription = input.prescription

        return patientRecordRepository.save(patientRecord).toDTO()

    }

    private fun PatientRecord.toDTO() = PatientRecordDTO(
        reason = this.reason,
        diagnostic = this.diagnostic,
        causal = this.causal,
        date = this.date,
        treatmentDescription = this.treatmentDescription,
        marrowRecord = this.marrowRecord,
        debit = this.debit,
        note = this.note,
        doctorName = this.user.fullName,
        prescription = this.prescription,
    )

}