package org.vn.dentalClinicMgt.domain.dto.specimen

import java.time.LocalDate

data class SpecimenDTO(
    val specimenId : Long,
    val specimenName : String,
    val patientName: String,
    val laboName: String,
    val receiveDate : LocalDate,
    val deliveryDate : LocalDate,
    val amount: Int,
    val price: Int,

    )
