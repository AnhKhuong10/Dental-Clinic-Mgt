package org.vn.dentalClinicMgt.service.impl

import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.materialExport.MaterialExportCreateInput
import org.vn.dentalClinicMgt.domain.dto.materialExport.MaterialExportDTO
import org.vn.dentalClinicMgt.domain.dto.materialExport.MaterialExportUpdateInput
import org.vn.dentalClinicMgt.domain.entity.MaterialExport
import org.vn.dentalClinicMgt.repository.MaterialExportRepository
import org.vn.dentalClinicMgt.repository.MaterialRepository
import org.vn.dentalClinicMgt.repository.PatientRecordRepository
import org.vn.dentalClinicMgt.repository.UserRepository
import org.vn.dentalClinicMgt.service.MaterialExportService
import org.vn.dentalClinicMgt.utils.SecurityUtil
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode
import java.time.LocalDate

@Service
class MaterialExportServiceImpl(
    private val materialExportRepository: MaterialExportRepository,
    private val userRepository: UserRepository,
    private val materialRepository: MaterialRepository,
    private val patientRecordRepository: PatientRecordRepository,
) : MaterialExportService {
    override fun getMaterialExportPage(
        search: String?,
        fromDate: LocalDate?,
        toDate: LocalDate?,
        page: Pageable
    ): Page<MaterialExportDTO> {
        val currentUser = SecurityUtil.getCurrentUserLogin().orElseThrow {
            BusinessException(ErrorCode.UNAUTHORIZED, "Not logged in.")
        }

        val user = userRepository.findByUsername(currentUser).orElseThrow {
            BusinessException(ErrorCode.NOT_FOUND, "user not found!")
        }

        val isAdmin = user.role.roleName.equals("ADMIN", ignoreCase = true)
        val isDoctor = user.role.roleName.equals("DOCTOR", ignoreCase = true)
        val showDeleted = isAdmin || isDoctor
        val materialExports = materialExportRepository.getMaterialExportPage(search, fromDate, toDate, showDeleted, page)
        return materialExports.map{ it.toDTO()}

    }

    override fun getMaterialExportDetail(materialExportId: Long): MaterialExportDTO {
        val materialExport = materialExportRepository.findById(materialExportId)
            .orElseThrow {BusinessException(ErrorCode.NOT_FOUND,"material Export not found")}

        return materialExport.toDTO()
    }

    @Transactional
    override fun createMaterialExport(input: MaterialExportCreateInput): MaterialExportDTO {

        val material = materialRepository.findById(input.materialId)
            .orElseThrow{ BusinessException(ErrorCode.NOT_FOUND,"material not found")}

        val patientRecord = patientRecordRepository.findById(input.patientRecordId)
            .orElseThrow{ BusinessException(ErrorCode.BAD_REQUEST, "Patient Record not found")}

        if(input.amount > material.amount){
            throw BusinessException(ErrorCode.BAD_REQUEST, "Amount not enough!")
        }

        val materialExport = MaterialExport(
            material = material,
            patientRecord = patientRecord,
            amount = input.amount,
            price = material.price,
            isDeleted = false
        )

        material.amount -= input.amount
        materialRepository.save(material)

        return materialExportRepository.save(materialExport).toDTO()
    }

    override fun updateMaterialExport(input: MaterialExportUpdateInput): MaterialExportDTO {
        val materialExport = materialExportRepository.findById(input.materialExportId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "Material Export not found") }

        val material = materialRepository.findById(input.materialId)
            .orElseThrow {
                BusinessException(ErrorCode.NOT_FOUND,
                    "material with id ${input.materialId} not found")
            }

        val patientRecord = patientRecordRepository.findById(input.patientRecordId)
            .orElseThrow{ BusinessException(ErrorCode.BAD_REQUEST, "Patient Record not found")}

        //Tính delta
        val oldAmount = materialExport.amount
        val newAmount = input.amount
        val delta = newAmount - oldAmount

        if (delta > 0 && material.amount < delta) {
            throw BusinessException(ErrorCode.BAD_REQUEST, "Not enough material in stock!")
        }

        material.amount -= delta
        materialRepository.save(material)

        materialExport.amount = newAmount
        materialExport.price = material.price
        materialExport.patientRecord = patientRecord
        materialExport.material = material
        return  materialExportRepository.save(materialExport).toDTO()
    }

    override fun deleteMaterialExport(materialExportId: Long): Boolean {
        val materialExport = materialExportRepository.findById(materialExportId)
            .orElseThrow {BusinessException(ErrorCode.NOT_FOUND,"material Export not found")}

        materialExport.isDeleted = true
        materialExportRepository.save(materialExport)
        return true
    }

    private fun MaterialExport.toDTO() = MaterialExportDTO(
        materialExportId = this.materialExportId,
        materialName = this.material.materialName,
        patientName = this.patientRecord.treatment.patient.patientName,
        amount = this.amount,
        price = this.material.price,
        totalPrice = this.material.price * this.amount,
        date = this.patientRecord.date
    )
}