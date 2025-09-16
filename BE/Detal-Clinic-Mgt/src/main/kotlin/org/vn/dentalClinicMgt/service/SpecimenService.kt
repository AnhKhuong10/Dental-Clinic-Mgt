package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.specimen.SpecimenCreateInput
import org.vn.dentalClinicMgt.domain.dto.specimen.SpecimenDTO
import org.vn.dentalClinicMgt.domain.dto.specimen.SpecimenUpdateInput
import java.time.LocalDate

interface SpecimenService {

    fun getListSpecimens(
        search: String?,
        fromDate: LocalDate?,
        toDate: LocalDate?,
        pageable: Pageable
        ): Page<SpecimenDTO>

    fun createSpecimen(input: SpecimenCreateInput): SpecimenDTO

    fun updateSpecimen(input: SpecimenUpdateInput): SpecimenDTO
}