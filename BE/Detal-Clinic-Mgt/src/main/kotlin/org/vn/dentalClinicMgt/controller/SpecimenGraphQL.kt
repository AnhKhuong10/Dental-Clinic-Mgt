package org.vn.dentalClinicMgt.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.labo.LaboPage
import org.vn.dentalClinicMgt.domain.dto.specimen.SpecimenCreateInput
import org.vn.dentalClinicMgt.domain.dto.specimen.SpecimenDTO
import org.vn.dentalClinicMgt.domain.dto.specimen.SpecimenPage
import org.vn.dentalClinicMgt.domain.dto.specimen.SpecimenUpdateInput
import org.vn.dentalClinicMgt.domain.entity.Specimen
import org.vn.dentalClinicMgt.service.SpecimenService
import java.time.LocalDate

@Controller
class SpecimenQuery (
    val specimenService: SpecimenService
){

    @QueryMapping
    fun getSpecimenPage(
        @Argument page: Int,
        @Argument size: Int,
        @Argument search: String?,
        @Argument fromDate: LocalDate?,
        @Argument toDate: LocalDate?,
    ): SpecimenPage {

        val pageable = PageRequest.of(page, size)
        val result = specimenService.getListSpecimens(search, fromDate, toDate, pageable)

        return SpecimenPage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageNumber = result.number,
            pageSize = result.size
        )

    }
}

@Controller
class SpecimenMutation(
    val specimenService: SpecimenService
){

    @MutationMapping
    fun createSpecimen(@Argument input: SpecimenCreateInput): SpecimenDTO {
        return specimenService.createSpecimen(input)
    }

    @MutationMapping
    fun updateSpecimen(@Argument input: SpecimenUpdateInput): SpecimenDTO {
        return specimenService.updateSpecimen(input)
    }
}