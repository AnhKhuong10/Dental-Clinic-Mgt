package org.vn.dentalClinicMgt.controller

import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.patientRecordServiceMap.PatientRecordServiceMapCreateInput
import org.vn.dentalClinicMgt.domain.dto.patientRecordServiceMap.PatientRecordServiceMapDTO
import org.vn.dentalClinicMgt.domain.dto.patientRecordServiceMap.PatientRecordServiceMapPage
import org.vn.dentalClinicMgt.service.PatientRecordServiceMapService

@Controller
class PatientRecordServiceQuery(
    private val patientRecordServiceMapService: PatientRecordServiceMapService
) {

    @QueryMapping
    @PreAuthorize("hasAuthority('PATIENT_RECORD_SERVICE_VIEW')")
    fun getPatientRecordServiceMapPage(
        @Argument page: Int,
        @Argument size: Int,
    ) : PatientRecordServiceMapPage{

        val pageable = PageRequest.of(page, size)
        val result = patientRecordServiceMapService.listPatientRecordService(pageable)

        return PatientRecordServiceMapPage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageNumber = result.number,
            pageSize = result.size,
        )
    }
}

@Controller
class PatientRecordServiceMutation(
    private val patientRecordServiceMapService: PatientRecordServiceMapService
){
    @MutationMapping
    @PreAuthorize("hasAuthority('PATIENT_RECORD_SERVICE_CREATE')")
    fun createPatientRecordService(
        @Argument input: PatientRecordServiceMapCreateInput
    ): List<PatientRecordServiceMapDTO>{
        return patientRecordServiceMapService.createPatientRecordService(input)
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('PATIENT_RECORD_SERVICE_INPROGRESS')")
    fun makeServiceAsInProgress(
        @Argument id: Long,
    ): PatientRecordServiceMapDTO{
        return patientRecordServiceMapService.makeServiceAsInProgress(id)
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('PATIENT_RECORD_SERVICE_DONE')")
    fun makeServiceAsDone(
        @Argument id: Long,
    ): PatientRecordServiceMapDTO{
        return patientRecordServiceMapService.makeServiceAsDone(id)
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('PATIENT_RECORD_SERVICE_CANCELLED')")
    fun makeServiceAsCancelled(
        @Argument id: Long,
    ): PatientRecordServiceMapDTO{
        return patientRecordServiceMapService.makeServiceAsCancelled(id)
    }


}
