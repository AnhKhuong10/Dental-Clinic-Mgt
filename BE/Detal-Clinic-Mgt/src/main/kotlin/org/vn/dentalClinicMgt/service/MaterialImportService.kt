package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.material.MaterialCreateInput
import org.vn.dentalClinicMgt.domain.dto.material.MaterialUpdateInput
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportCreateInput
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportDTO
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportUpdateInput
import org.vn.dentalClinicMgt.domain.entity.MaterialImport
import java.time.LocalDate

interface MaterialImportService {

    fun getListMaterials(
        search: String?,
        fromDate: LocalDate?,
        toDate: LocalDate?,
        page: Pageable,
    ): Page<MaterialImportDTO>

    fun getMaterialImportDetails(materialImportId: Long): MaterialImportDTO

    fun createMaterial(input: MaterialImportCreateInput): MaterialImportDTO

    fun updateMaterial(input: MaterialImportUpdateInput): MaterialImportDTO

    fun deleteMaterial(materialImportId: Long): Boolean
}