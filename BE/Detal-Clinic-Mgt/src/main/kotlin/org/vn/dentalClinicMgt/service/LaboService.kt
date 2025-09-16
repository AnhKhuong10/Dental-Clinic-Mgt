package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.labo.LaboCreateInput
import org.vn.dentalClinicMgt.domain.dto.labo.LaboDTO
import org.vn.dentalClinicMgt.domain.dto.labo.LaboUpdateInput

interface LaboService {

    fun getListLabo(search: String? ,page: Pageable): Page<LaboDTO>

    fun getLaboById(laboId: Long): LaboDTO

    fun createLabo(input: LaboCreateInput): LaboDTO

    fun updateLabo(input: LaboUpdateInput): LaboDTO
}