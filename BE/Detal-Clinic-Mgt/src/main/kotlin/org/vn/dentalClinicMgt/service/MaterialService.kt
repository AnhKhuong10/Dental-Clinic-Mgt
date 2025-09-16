package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.material.MaterialCreateInput
import org.vn.dentalClinicMgt.domain.dto.material.MaterialDTO
import org.vn.dentalClinicMgt.domain.dto.material.MaterialUpdateInput

interface MaterialService {

    fun getListMaterials(
        search: String?,
        page: Pageable
    ) : Page<MaterialDTO>

    fun getMaterialDetails(materialId: Long) : MaterialDTO

    fun createMaterial(input : MaterialCreateInput) : MaterialDTO

    fun updateMaterial(input : MaterialUpdateInput) : MaterialDTO
}