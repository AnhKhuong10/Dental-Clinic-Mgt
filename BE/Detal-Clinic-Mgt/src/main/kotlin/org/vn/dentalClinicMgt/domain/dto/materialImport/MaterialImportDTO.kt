package org.vn.dentalClinicMgt.domain.dto.materialImport

import java.time.LocalDate

data class MaterialImportDTO(
    val materialImportId: Long,
    val supplyName: String,
    val materialName: String,
    val date: LocalDate,
    val amount: Int,
    val price: Int,
    val totalPrice: Int,
)
