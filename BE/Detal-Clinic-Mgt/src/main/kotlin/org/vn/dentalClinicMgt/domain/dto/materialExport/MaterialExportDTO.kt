package org.vn.dentalClinicMgt.domain.dto.materialExport

import java.time.LocalDate

data class MaterialExportDTO(
    val materialExportId: Long,
    val materialName: String,
    val patientName: String,
    val amount: Int,
    val price: Int,
    val totalPrice: Int,
    val date: LocalDate,
)
