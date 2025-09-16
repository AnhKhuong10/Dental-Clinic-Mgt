package org.vn.dentalClinicMgt.domain.dto.specimen

import java.time.LocalDate

data class SpecimenCreateInput(
    val specimenName : String,
    val receiveDate : LocalDate,
    val deliveryDate : LocalDate,
    val amount: Int,
    val price: Int,
    val patienRecordId: Long,
    val laboId: Long,
)
