package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.material.MaterialCreateInput
import org.vn.dentalClinicMgt.domain.dto.material.MaterialDTO
import org.vn.dentalClinicMgt.domain.dto.material.MaterialUpdateInput
import org.vn.dentalClinicMgt.domain.entity.Material
import org.vn.dentalClinicMgt.repository.MaterialRepository
import org.vn.dentalClinicMgt.service.MaterialService
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode

@Service
class MaterialServiceImpl (
    private val materialRepository: MaterialRepository
) : MaterialService {
    override fun getListMaterials(search: String?, page: Pageable): Page<MaterialDTO> {
        val listMaterials = materialRepository.getListMaterials(search, page)

        return listMaterials.map { it.toDTO() }
    }

    override fun getMaterialDetails(materialId: Long): MaterialDTO {
        val material = materialRepository.findById(materialId)
        .orElseThrow { BusinessException(ErrorCode.NOT_FOUND ,"Unknown material ID: $materialId") }
        return material.toDTO()
    }

    override fun createMaterial(input: MaterialCreateInput): MaterialDTO {
        if(materialRepository.existsMaterialsByMaterialName(input.materialName)){
            throw BusinessException(ErrorCode.BAD_REQUEST, "Material name ${input.materialName} already exists")
        }

        val material = Material(
            materialName = input.materialName,
            unit = input.unit,
            amount = input.amount,
            price = input.price,
        )

        return materialRepository.save(material).toDTO()
    }

    override fun updateMaterial(input: MaterialUpdateInput): MaterialDTO {
        val material = materialRepository.findById(input.materialId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND ,"Unknown material ID: ${input.materialId}") }

        material.materialName = input.materialName
        material.unit = input.unit
        material.amount = input.amount
        material.price = input.price
        return materialRepository.save(material).toDTO()

    }

    private fun Material.toDTO() = MaterialDTO(
        materialId = this.materialId,
        materialName = this.materialName,
        unit = this.unit,
        amount = this.amount,
        price = this.price,
        totalPrice = this.amount * this.price,
    )

}