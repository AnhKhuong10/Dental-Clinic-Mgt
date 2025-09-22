package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportCreateInput
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportDTO
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportUpdateInput
import org.vn.dentalClinicMgt.domain.entity.MaterialImport
import org.vn.dentalClinicMgt.domain.entity.User
import org.vn.dentalClinicMgt.repository.MaterialImportRepository
import org.vn.dentalClinicMgt.repository.MaterialRepository
import org.vn.dentalClinicMgt.repository.UserRepository
import org.vn.dentalClinicMgt.service.MaterialImportService
import org.vn.dentalClinicMgt.utils.SecurityUtil
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode
import java.time.LocalDate

@Service
class MaterialImportServiceImpl (
    private val materialImportRepository: MaterialImportRepository,
    private val materialRepository: MaterialRepository,
    private val userRepository: UserRepository
) : MaterialImportService {

    override fun getListMaterials(
        search: String?,
        fromDate: LocalDate?,
        toDate: LocalDate?,
        page: Pageable,
    ): Page<MaterialImportDTO> {
        val currentUser = SecurityUtil.getCurrentUserLogin().orElseThrow {
            BusinessException(ErrorCode.UNAUTHORIZED, "Not logged in!")
        }

        val user = userRepository.findByUsername(currentUser).orElseThrow {
            BusinessException(ErrorCode.NOT_FOUND, "user not found!")
        }

        val isAdmin = user.role.roleName.equals("ADMIN", ignoreCase = true)
        val isLeaderNurse = user.role.roleName.equals("LEADER_NURSE", ignoreCase = true)
        val isNurse = user.role.roleName.equals("NURSE", ignoreCase = true)

        val showDeleted = isAdmin || isLeaderNurse

        val materials = materialImportRepository.getListMaterialsImport(search,fromDate, toDate, showDeleted, page)
        return materials.map { it.toDto() }
    }

    override fun getMaterialImportDetails(materialImportId: Long): MaterialImportDTO {
        val materialImport = materialImportRepository.findById(materialImportId)
            .orElseThrow{
                throw BusinessException(ErrorCode.NOT_FOUND, "materialImport not found")
            }
        return materialImport.toDto()
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
            .orElseThrow {
                BusinessException(ErrorCode.NOT_FOUND,
                    "materialImport with id ${input.materialImportId} not found")
            }

        val material = materialRepository.findById(input.materialId)
            .orElseThrow {
                BusinessException(ErrorCode.NOT_FOUND,
                    "material with id ${input.materialId} not found")
            }

        // Tính delta
        val oldAmount = materialImport.amount
        val newAmount = input.amount
        val delta = newAmount - oldAmount

        // Update materialImport
        materialImport.material = material
        materialImport.date = input.date
        materialImport.amount = newAmount
        materialImport.supplyName = input.supplyName
        materialImport.price = input.price

        // Update material (theo delta)
        material.amount += delta
        material.price = input.price

        materialRepository.save(material)
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