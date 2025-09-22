package org.vn.dentalClinicMgt.domain.dto.materialImport

import java.time.LocalDate

data class MaterialImportCreateInput(
    val materialId: Long,
    val supplyName: String,
    val date: LocalDate,
    val amount: Int,
    val price: Int,
)
