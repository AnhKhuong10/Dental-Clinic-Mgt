package org.vn.dentalClinicMgt.service

import org.vn.dentalClinicMgt.domain.dto.treatment.CreateTreatmentInput
import org.vn.dentalClinicMgt.domain.dto.treatment.TreatmentDTO
import org.vn.dentalClinicMgt.domain.entity.Treatment

interface TreatmentService {

    fun createTreatment(input: CreateTreatmentInput): TreatmentDTO
}