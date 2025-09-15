package org.vn.dentalClinicMgt.service.impl

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.treatment.CreateTreatmentInput
import org.vn.dentalClinicMgt.domain.dto.treatment.TreatmentDTO
import org.vn.dentalClinicMgt.domain.dto.treatment.TreatmentServiceMapDTO
import org.vn.dentalClinicMgt.domain.entity.Treatment
import org.vn.dentalClinicMgt.domain.entity.TreatmentServiceMap
import org.vn.dentalClinicMgt.repository.PatientRepository
import org.vn.dentalClinicMgt.repository.ServiceRepository
import org.vn.dentalClinicMgt.repository.TreatmentRepository
import org.vn.dentalClinicMgt.repository.TreatmentServiceMapRepository
import org.vn.dentalClinicMgt.service.TreatmentService
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode

@Service
class TreatmentServiceImpl(
    private val treatmentRepository: TreatmentRepository,
    private val patientRepository: PatientRepository,
    private val serviceRepository: ServiceRepository,
    private val treatmentServiceMapRepository: TreatmentServiceMapRepository
): TreatmentService {

    @Transactional
    override fun createTreatment(input: CreateTreatmentInput): TreatmentDTO {
        val patient = patientRepository.findById(input.patientId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "Patient ${input.patientId} not found") }

        val treatment = treatmentRepository.save(
            Treatment(patient = patient)
        )

        val serviceMaps = input.serviceIds.map { serviceId ->
            val service = serviceRepository.findById(serviceId)
                .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "Service $serviceId not found") }

            val mapEntity = treatmentServiceMapRepository.save(
                TreatmentServiceMap(
                    treatment = treatment,
                    service = service,
                    currentPrice = service.price,
                    discount = 0,
                    startRecord = null
                )
            )
            mapEntity.toDTO()
        }

        return TreatmentDTO(
            treatmentId = treatment.treatmentId,
            patientId = patient.patientId,
            services = serviceMaps
        )
    }

    override fun getTreatmentById(id: Long): TreatmentDTO {
        val treatment = treatmentRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND,"Treatment not found") }
        val serviceMaps = treatmentServiceMapRepository.findByTreatmentId(treatment.treatmentId)
        return treatment.toDTO(serviceMaps)
    }

    override fun getTreatmentsByPatient(patientId: Long): List<TreatmentDTO> {
        val treatments = treatmentRepository.findByPatientId(patientId)
        return treatments.map { t ->
            val serviceMaps = treatmentServiceMapRepository.findByTreatmentId(t.treatmentId)
            t.toDTO(serviceMaps)
        }
    }

    private fun TreatmentServiceMap.toDTO() = TreatmentServiceMapDTO(
        treatmentServiceMapId = this.treatmentServiceMapId,
        serviceName = this.service?.serviceName ?: "",
        price = this.currentPrice,
        discount = this.discount?:0
    )

    private fun Treatment.toDTO(serviceMaps: List<TreatmentServiceMap>) = TreatmentDTO(
        treatmentId = this.treatmentId,
        patientId = this.patient.patientId,
        services = serviceMaps.map { it.toDTO() }
    )


}