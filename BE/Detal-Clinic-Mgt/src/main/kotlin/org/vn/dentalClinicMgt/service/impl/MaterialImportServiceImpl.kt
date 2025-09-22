package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.material.MaterialCreateInput
import org.vn.dentalClinicMgt.domain.dto.material.MaterialDTO
import org.vn.dentalClinicMgt.domain.dto.material.MaterialUpdateInput
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportCreateInput
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportDTO
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportUpdateInput
import org.vn.dentalClinicMgt.domain.entity.Material
import org.vn.dentalClinicMgt.domain.entity.MaterialImport
import org.vn.dentalClinicMgt.repository.MaterialImportRepository
import org.vn.dentalClinicMgt.repository.MaterialRepository
import org.vn.dentalClinicMgt.service.MaterialImportService
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode
import java.time.LocalDate

class MaterialImportServiceImpl (
    private val materialImportRepository: MaterialImportRepository,
    private val materialRepository: MaterialRepository
) : MaterialImportService {

    override fun getListMaterials(
        search: String?,
        fromDate: LocalDate?,
        toDate: LocalDate?,
        page: Pageable
    ): Page<MaterialImportDTO> {

        val materials = materialImportRepository.getListMaterialsImport(search,fromDate, toDate, page)
        return materials.map { it.toDto() }
    }

    override fun createMaterial(input: MaterialImportCreateInput): MaterialImportDTO {

        val material = materialRepository.findById(input.materialId)
            .orElseThrow{BusinessException(ErrorCode.NOT_FOUND,"material with id ${input.materialId} not found")}

        val materialImport = MaterialImport(
            material = material,
            date = input.date,
            amount = input.amount,
            supplyName = input.supplyName,
            price = input.price,
            isDeleted = false,
        )

        material.amount += input.amount
        material.price  = input.price
        materialRepository.save(material)

        return materialImportRepository.save(materialImport).toDto()
     }

    override fun updateMaterial(input: MaterialImportUpdateInput): MaterialImportDTO {
        val materialImport = materialImportRepository.findById(input.materialImportId)
            .orElseThrow{
                BusinessException(ErrorCode.NOT_FOUND,"materialImport with id ${input.materialImportId} not found")
            }

        val material = materialRepository.findById(input.materialId)
            .orElseThrow{BusinessException(ErrorCode.NOT_FOUND,"material with id ${input.materialId} not found")}

        materialImport.material = material
        materialImport.date = input.date
        materialImport.amount = input.amount
        materialImport.supplyName = input.supplyName
        materialImport.price = input.price

        return materialImportRepository.save(materialImport).toDto()
    }

    override fun deleteMaterial(materialImportId: Long): Boolean {
        val materialImport = materialImportRepository.findById(materialImportId)
            .orElseThrow{
                BusinessException(ErrorCode.NOT_FOUND,"materialImport with id $materialImportId not found")
            }
        materialImport.isDeleted = true
        materialImportRepository.save(materialImport)
        return true
    }

    private fun MaterialImport.toDto() = MaterialImportDTO (
        materialImportId = this.materialImportId,
        supplyName = this.supplyName,
        materialName = this.material.materialName,
        date = this.date,
        amount = this.amount,
        price = this.price,
        totalPrice = this.price * this.amount,
    )
}