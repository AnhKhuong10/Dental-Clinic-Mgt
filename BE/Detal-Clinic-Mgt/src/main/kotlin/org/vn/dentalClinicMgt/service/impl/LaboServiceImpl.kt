package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.labo.LaboCreateInput
import org.vn.dentalClinicMgt.domain.dto.labo.LaboDTO
import org.vn.dentalClinicMgt.domain.dto.labo.LaboUpdateInput
import org.vn.dentalClinicMgt.domain.entity.Labo
import org.vn.dentalClinicMgt.repository.LaboRepository
import org.vn.dentalClinicMgt.service.LaboService
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode

@Service
class LaboServiceImpl(
    private val laboRepository: LaboRepository,
) : LaboService {
    override fun getListLabo(search: String? ,page: Pageable): Page<LaboDTO> {

        return laboRepository.getListLabo(search, page).map { it.toDTO() }
    }

    override fun getLaboById(laboId: Long): LaboDTO {
        val labo = laboRepository.findById(laboId).orElseThrow{
            BusinessException(ErrorCode.NOT_FOUND, "Labo Not Found")
        }
        return labo.toDTO()
    }

    override fun createLabo(input: LaboCreateInput): LaboDTO {

        if(laboRepository.existsByLaboName(input.laboName)) {
            throw BusinessException(ErrorCode.BAD_REQUEST, "Labo Name Already Exists")
        }

        if(laboRepository.existsByPhone(input.phone)) {
            throw BusinessException(ErrorCode.BAD_REQUEST, "Phone Already Exists")
        }

        val labo = Labo(
            laboName = input.laboName,
            phone = input.phone,
            isDeleted = false,
        )
        return laboRepository.save(labo).toDTO()
    }

    override fun updateLabo(input: LaboUpdateInput): LaboDTO {
        val labo = laboRepository.findById(input.laboId).orElseThrow{
            throw BusinessException(ErrorCode.NOT_FOUND, "Labo Not Found")
        }

        labo.laboName = input.laboName
        labo.phone = input.phone

        return laboRepository.save(labo).toDTO()
    }

    private fun Labo.toDTO() = LaboDTO (
        laboId = this.laboId,
        laboName = this.laboName,
        phone = this.phone
    )
}
