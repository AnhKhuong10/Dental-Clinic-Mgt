package org.vn.dentalClinicMgt.controller

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.treatment.CreateTreatmentInput
import org.vn.dentalClinicMgt.domain.dto.treatment.TreatmentDTO
import org.vn.dentalClinicMgt.service.TreatmentService

@Controller
class TreatmentMutation(
    private val treatmentService: TreatmentService
){

    @PreAuthorize("hasAuthority('TREATMENT_CREATE')")
    @MutationMapping
    fun createTreatment(@Argument input: CreateTreatmentInput): TreatmentDTO {

        return treatmentService.createTreatment(input)
    }
}


@Controller
class TreatmentQuery(
    private val treatmentService: TreatmentService
) {

    @PreAuthorize("hasAuthority('TREATMENT_VIEW_DETTAIL')")
    @QueryMapping
    fun getTreatmentById(@Argument treatmentId : Long): TreatmentDTO {
        return treatmentService.getTreatmentById(treatmentId)
    }

    @PreAuthorize("hasAuthority('TREATMENT_BY_PATIENT_VIEW')")
    @QueryMapping
    fun getTreatmentsByPatient(@Argument patientId: Long): List<TreatmentDTO> {
        return treatmentService.getTreatmentsByPatient(patientId)
    }
}