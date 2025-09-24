package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.patientRecordServiceMap.PatientRecordServiceMapCreateInput
import org.vn.dentalClinicMgt.domain.dto.patientRecordServiceMap.PatientRecordServiceMapDTO
import org.vn.dentalClinicMgt.domain.entity.PatientRecordServiceMap
import org.vn.dentalClinicMgt.repository.PatientRecordRepository
import org.vn.dentalClinicMgt.repository.PatientRecordServiceMapRepository
import org.vn.dentalClinicMgt.repository.ServiceRepository
import org.vn.dentalClinicMgt.repository.TreatmentServiceMapRepository
import org.vn.dentalClinicMgt.service.PatientRecordServiceMapService
import org.vn.dentalClinicMgt.utils.constants.PatientRecordServiceStatus
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode

@Service
class PatientRecordServiceMapServiceImpl (
    private val patientRecordServiceMapRepository: PatientRecordServiceMapRepository,
    private val patientRecordRepository: PatientRecordRepository,
    private val serviceRepository: ServiceRepository,
    private val treatmentServiceMapRepository: TreatmentServiceMapRepository
) : PatientRecordServiceMapService {

    override fun listPatientRecordService(pageable: Pageable): Page<PatientRecordServiceMapDTO> {
        val result = patientRecordServiceMapRepository.listPatientRecordService(pageable)
        return result.map { it.toDto() }
    }

    override fun createPatientRecordService(input: PatientRecordServiceMapCreateInput): List<PatientRecordServiceMapDTO> {

        val patientRecord = patientRecordRepository.findById(input.patientRecordId)
            .orElseThrow{ BusinessException(ErrorCode.NOT_FOUND, "Patient Record not found!") }

        // Check trùng serviceId trong patientRecord
        input.serviceIds.forEach { serviceId ->
            if (patientRecordServiceMapRepository.existsByPatientRecordIdAndServiceId(input.patientRecordId, serviceId)) {
                throw BusinessException(
                    ErrorCode.BAD_REQUEST,
                    "Service with id $serviceId already exists in PatientRecord ${input.patientRecordId}."
                )
            }

            // Check service có thuộc treatment không
            if (!treatmentServiceMapRepository.existsByTreatmentTreatmentIdAndServiceServiceId(
                    patientRecord.treatment.treatmentId, serviceId
                )) {
                throw BusinessException(
                    ErrorCode.BAD_REQUEST,
                    "Service $serviceId does not belong to Treatment ${patientRecord.treatment.treatmentId}"
                )
            }
        }

        return input.serviceIds.map { serviceId ->
            val service = serviceRepository.findById(serviceId)
                .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "Service with id $serviceId not found!") }

            val mapEntity = patientRecordServiceMapRepository.save(
                PatientRecordServiceMap(
                    patientRecord = patientRecord,
                    service = service,
                    status = PatientRecordServiceStatus.PENDING
                )
            )

            mapEntity.toDto()
        }
    }

    override fun makeServiceAsInProgress(id: Long): PatientRecordServiceMapDTO {
         val service = patientRecordServiceMapRepository.findById(id)
             .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "Patient Record not found!") }

        if(service.status != PatientRecordServiceStatus.PENDING) {
            throw BusinessException(ErrorCode.BAD_REQUEST, "Service with id $id is not pending!")
        }
        service.status = PatientRecordServiceStatus.IN_PROGRESS

        return patientRecordServiceMapRepository.save(service).toDto()

    }

    override fun makeServiceAsDone(id: Long): PatientRecordServiceMapDTO {
        val service = patientRecordServiceMapRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "Patient Record not found!") }

        if(service.status != PatientRecordServiceStatus.IN_PROGRESS) {
            throw BusinessException(ErrorCode.BAD_REQUEST, "Service with id $id is not In_Progress!")
        }
        service.status = PatientRecordServiceStatus.DONE

        return patientRecordServiceMapRepository.save(service).toDto()
    }

    override fun makeServiceAsCancelled(id: Long): PatientRecordServiceMapDTO {
        val service = patientRecordServiceMapRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "Patient Record not found!") }

        if(service.status == PatientRecordServiceStatus.DONE || service.status == PatientRecordServiceStatus.CANCELLED) {
            throw BusinessException(ErrorCode.BAD_REQUEST, "Service with id $id is DONE or CANCELLED. Cannot cancel service!")
        }

        service.status = PatientRecordServiceStatus.CANCELLED

        return patientRecordServiceMapRepository.save(service).toDto()
    }


    private fun PatientRecordServiceMap.toDto()= PatientRecordServiceMapDTO (
        serviceName = this.service.serviceName,
        servicePrice = this.service.price,
        status = this.status,
    )

}