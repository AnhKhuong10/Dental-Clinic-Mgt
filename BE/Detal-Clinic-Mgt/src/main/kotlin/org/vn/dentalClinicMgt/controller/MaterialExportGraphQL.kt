package org.vn.dentalClinicMgt.controller

import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.materialExport.MaterialExportCreateInput
import org.vn.dentalClinicMgt.domain.dto.materialExport.MaterialExportDTO
import org.vn.dentalClinicMgt.domain.dto.materialExport.MaterialExportPage
import org.vn.dentalClinicMgt.domain.dto.materialExport.MaterialExportUpdateInput
import org.vn.dentalClinicMgt.service.MaterialExportService
import java.time.LocalDate

@Controller
class  MaterialExportQuery(
    private val materialExportService: MaterialExportService,
){

    @PreAuthorize("hasAuthority('MATERIAL_EXPORT_VIEW')")
    @QueryMapping
    fun getMaterialExportPage(
        @Argument page: Int,
        @Argument size: Int,
        @Argument search: String?,
        @Argument fromDate: LocalDate?,
        @Argument toDate: LocalDate?
    ) : MaterialExportPage {
        val pageable = PageRequest.of(page, size)
        val result = materialExportService.getMaterialExportPage(search, fromDate, toDate, pageable)

        return MaterialExportPage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageNumber = result.number,
            pageSize = result.size,
        )
    }

    @PreAuthorize("hasAuthority('MATERIAL_EXPORT_DETAIL')")
    @QueryMapping
    fun getMaterialExportDetail(@Argument id: Long): MaterialExportDTO{

        return materialExportService.getMaterialExportDetail(id)
    }
}

@Controller
class MaterialExportMutation(
    private val materialExportService: MaterialExportService,
) {

    @PreAuthorize("hasAuthority('MATERIAL_EXPORT_CREATE')")
    @MutationMapping
    fun createMaterialExport(@Argument input: MaterialExportCreateInput): MaterialExportDTO{
        return materialExportService.createMaterialExport(input)
    }

    @PreAuthorize("hasAuthority('MATERIAL_EXPORT_UPDATE')")
    @MutationMapping
    fun updateMaterialExport(@Argument input: MaterialExportUpdateInput): MaterialExportDTO{
        return materialExportService.updateMaterialExport(input)
    }

    @PreAuthorize("hasAuthority('MATERIAL_EXPORT_DELETE')")
    @MutationMapping
    fun deleteMaterialExport(@Argument id: Long): Boolean{
        return materialExportService.deleteMaterialExport(id)
    }

}