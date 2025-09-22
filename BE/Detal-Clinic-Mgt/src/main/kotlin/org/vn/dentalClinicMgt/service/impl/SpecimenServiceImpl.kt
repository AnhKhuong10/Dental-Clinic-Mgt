package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.specimen.SpecimenCreateInput
import org.vn.dentalClinicMgt.domain.dto.specimen.SpecimenDTO
import org.vn.dentalClinicMgt.domain.dto.specimen.SpecimenUpdateInput
import org.vn.dentalClinicMgt.domain.entity.Specimen
import org.vn.dentalClinicMgt.repository.LaboRepository
import org.vn.dentalClinicMgt.repository.PatientRecordRepository
import org.vn.dentalClinicMgt.repository.PatientRepository
import org.vn.dentalClinicMgt.repository.SpecimenRepository
import org.vn.dentalClinicMgt.service.SpecimenService
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode
import java.time.LocalDate

@Service
class SpecimenServiceImpl(
    private val specimenRepository: SpecimenRepository,
    private val patientRecordRepository: PatientRecordRepository,
    private val laboRepository: LaboRepository,
) : SpecimenService {

    override fun getListSpecimens(
        search: String?,
        fromDate: LocalDate?,
        toDate: LocalDate?,
        pageable: Pageable
    ): Page<SpecimenDTO> {
        val specimens = specimenRepository.getListSpecimens(search, fromDate,toDate, pageable)
        return specimens.map { it.toDTO() }
    }

    override fun createSpecimen(input: SpecimenCreateInput): SpecimenDTO {
        val patientRecord = patientRecordRepository.findById(input.patienRecordId)
            .orElseThrow{
                BusinessException(ErrorCode.NOT_FOUND,"PatientRecord not found")
            }

        val labo = laboRepository.findById(input.laboId)
            .orElseThrow{
                BusinessException(ErrorCode.NOT_FOUND,"Labo not found")
            }

        val specimen = Specimen(
            specimenName = input.specimenName,
            receiveDate = input.receiveDate,
            deliveryDate = input.deliveryDate,
            amount = input.amount,
            price = input.price,
            patientRecord = patientRecord,
            labo = labo,
        )

        return specimenRepository.save(specimen).toDTO()
    }

    override fun updateSpecimen(input: SpecimenUpdateInput): SpecimenDTO {

        val specimen = specimenRepository.findById(input.specimenId)
            .orElseThrow{
                BusinessException(ErrorCode.NOT_FOUND,"Specimen not found")
            }

        val patientRecord = patientRecordRepository.findById(input.patienRecordId)
            .orElseThrow{
                BusinessException(ErrorCode.NOT_FOUND,"PatientRecord not found")
            }

        val labo = laboRepository.findById(input.laboId)
            .orElseThrow{
                BusinessException(ErrorCode.NOT_FOUND,"Lobo not found")
            }

        specimen.specimenName = input.specimenName
        specimen.receiveDate = input.receiveDate
        specimen.deliveryDate = input.deliveryDate
        specimen.amount = input.amount
        specimen.price = input.price
        specimen.patientRecord = patientRecord
        specimen.labo = labo

        return specimenRepository.save(specimen).toDTO()
    }

    private fun Specimen.toDTO() = SpecimenDTO(
        specimenId = this.specimenId,
        specimenName = this.specimenName,
        patientName =  this.patientRecord.treatment.patient.patientName,
        laboName = this.labo.laboName,
        receiveDate = this.receiveDate,
        deliveryDate = this.deliveryDate,
        amount = this.amount,
        price = this.price,
    )
}