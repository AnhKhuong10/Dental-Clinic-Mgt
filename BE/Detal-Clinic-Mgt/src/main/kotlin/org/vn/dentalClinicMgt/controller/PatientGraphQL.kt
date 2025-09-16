package org.vn.dentalClinicMgt.controller

import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.patient.PatientCreateInput
import org.vn.dentalClinicMgt.domain.dto.patient.PatientDTO
import org.vn.dentalClinicMgt.domain.dto.patient.PatientPage
import org.vn.dentalClinicMgt.domain.dto.patient.PatientUpdateInput
import org.vn.dentalClinicMgt.service.PatientService
import org.vn.dentalClinicMgt.utils.constants.GenderEnum
import org.vn.dentalClinicMgt.utils.constants.PatientStatus

@Controller
class PatientQuery(
    private val patientService: PatientService,
){

    @PreAuthorize("hasAuthority('PATIENT_VIEW')")
    @QueryMapping
    fun getPatientPage(
        @Argument page: Int,
        @Argument size: Int,
        @Argument search: String?,
        @Argument gender: GenderEnum?,
        @Argument status: PatientStatus?
    ): PatientPage {
        val pageable = PageRequest.of(page, size)
        val result = patientService.listPatients(search, gender, status, pageable)

        return PatientPage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageNumber = result.number,
            pageSize = result.size
        )
    }

    @PreAuthorize("hasAuthority('PATIENT_VIEW_DETAIL')")
    @QueryMapping
    fun getPatientDetail(@Argument id: Long): PatientDTO?{
        return patientService.getPatientByPatientId(id)

    }
}

@Controller
class PatientMutation(
    private val patientService: PatientService,
){
    @PreAuthorize("hasAuthority('PATIENT_CREATE')")
    @MutationMapping
    fun createPatient(@Argument @Valid input: PatientCreateInput): PatientDTO {
        println("Create patient = $input\n")
        return patientService.createPatient(input)
    }

    @PreAuthorize("hasAuthority('PATIENT_UPDATE')")
    @MutationMapping
    fun updatePatient(@Argument @Valid input: PatientUpdateInput): PatientDTO {
        println("Update patient = $input\n")
        return patientService.updatePatient(input)
    }
}