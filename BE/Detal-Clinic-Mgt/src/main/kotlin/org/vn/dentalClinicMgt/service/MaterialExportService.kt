package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.materialExport.MaterialExportCreateInput
import org.vn.dentalClinicMgt.domain.dto.materialExport.MaterialExportDTO
import org.vn.dentalClinicMgt.domain.dto.materialExport.MaterialExportPage
import org.vn.dentalClinicMgt.domain.dto.materialExport.MaterialExportUpdateInput
import org.vn.dentalClinicMgt.domain.entity.MaterialExport
import java.time.LocalDate

interface MaterialExportService {

    fun getMaterialExportPage(search: String?,
                              fromDate: LocalDate?,
                              toDate: LocalDate?,
                              page: Pageable): Page<MaterialExportDTO>

    fun getMaterialExportDetail(materialExportId : Long): MaterialExportDTO

    fun createMaterialExport(input: MaterialExportCreateInput): MaterialExportDTO
    fun updateMaterialExport(input: MaterialExportUpdateInput): MaterialExportDTO

    fun deleteMaterialExport(materialExportId: Long): Boolean
}