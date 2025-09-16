package org.vn.dentalClinicMgt.controller

import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.patientRecord.PatientRecordCreateInput
import org.vn.dentalClinicMgt.domain.dto.patientRecord.PatientRecordDTO
import org.vn.dentalClinicMgt.domain.dto.patientRecord.PatientRecordPage
import org.vn.dentalClinicMgt.domain.dto.patientRecord.PatientRecordUpdateInput
import org.vn.dentalClinicMgt.service.PatientRecordService
import java.time.LocalDate

@Controller
class PatientRecordQuery(
    private val patientRecordService: PatientRecordService,
) {

    @QueryMapping
    @PreAuthorize("hasAuthority('PATIENT_RECORD_VIEW')")
    fun getPatientRecordPage(
        @Argument page: Int,
        @Argument size: Int,
        @Argument search: String?,
        @Argument fromDate: LocalDate?,
        @Argument toDate: LocalDate?,
    ): PatientRecordPage{

        val pageable = PageRequest.of(page, size)
        val result = patientRecordService.listPatientRecords(search, fromDate, toDate, pageable)

        return PatientRecordPage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageNumber = result.number,
            pageSize = result.size
        )
    }
}

@Controller
class PatientRecordMutation(
    private val patientRecordService: PatientRecordService,
) {

    @MutationMapping
    @PreAuthorize("hasAuthority('PATIENT_RECORD_CREATE')")
    fun createPatientRecord(@Argument input: PatientRecordCreateInput): PatientRecordDTO {

        return patientRecordService.createPatientRecord(input)
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('PATIENT_RECORD_UPDATE')")
    fun updatePatientRecord(@Argument input: PatientRecordUpdateInput): PatientRecordDTO {

        return patientRecordService.updatePatientRecord(input)
    }

}