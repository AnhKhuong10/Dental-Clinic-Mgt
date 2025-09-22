package org.vn.dentalClinicMgt.controller

import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportCreateInput
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportDTO
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportPage
import org.vn.dentalClinicMgt.domain.dto.materialImport.MaterialImportUpdateInput
import org.vn.dentalClinicMgt.service.MaterialImportService
import java.time.LocalDate

@Controller
class MaterialImportQuery(
    private val materialImportService: MaterialImportService,
){

    @PreAuthorize("hasAuthority('MATERIAL_IMPORT_VIEW')")
    @QueryMapping
    fun getMaterialImportPage(
        @Argument page: Int,
        @Argument size: Int,
        @Argument fromDate: LocalDate?,
        @Argument toDate: LocalDate?,
        @Argument search: String?,
    ) : MaterialImportPage{
        val pageable = PageRequest.of(page, size)
        val result = materialImportService.getListMaterials(search, fromDate, toDate, pageable)

        return MaterialImportPage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageSize = result.size,
            pageNumber = result.number,
        )

    }

    @PreAuthorize("hasAuthority('MATERIAL_IMPORT_DETIAL')")
    @QueryMapping
    fun getMaterialImportDetail(@Argument id: Long): MaterialImportDTO{

        return materialImportService.getMaterialImportDetails(id)
    }
}

@Controller
class MaterialImportMutation(
    private val materialImportService: MaterialImportService,
){

    @PreAuthorize("hasAuthority('MATERIAL_IMPORT_CREATE')")
    @MutationMapping
    fun createMaterialImport(@Argument input: MaterialImportCreateInput) : MaterialImportDTO {

        return materialImportService.createMaterial(input)
    }

    @PreAuthorize("hasAuthority('MATERIAL_IMPORT_UPDATE')")
    @MutationMapping
    fun updateMaterialImport(@Argument input: MaterialImportUpdateInput) : MaterialImportDTO {

        return materialImportService.updateMaterial(input)
    }

    @PreAuthorize("hasAuthority('MATERIAL_IMPORT_DELETE')")
    @MutationMapping
    fun deleteMaterialImport(@Argument id: Long): Boolean {

        return materialImportService.deleteMaterial(id)
    }

}
