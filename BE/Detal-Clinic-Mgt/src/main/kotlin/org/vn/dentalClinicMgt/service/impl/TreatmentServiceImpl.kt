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

        // Tạo treatment
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
            id = treatment.treatmentId,
            patientId = patient.patientId,
            services = serviceMaps
        )
    }

    fun TreatmentServiceMap.toDTO() = TreatmentServiceMapDTO(
        id = this.treatmentServiceMapId,
        serviceName = this.service?.serviceName ?: "",
        price = this.currentPrice,
        discount = this.discount?:0
    )


}