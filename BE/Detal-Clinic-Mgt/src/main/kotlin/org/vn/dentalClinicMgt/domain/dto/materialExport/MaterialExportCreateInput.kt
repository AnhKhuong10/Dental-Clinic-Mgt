package org.vn.dentalClinicMgt.domain.dto.materialExport

data class MaterialExportCreateInput(
    val materialId: Long,
    val patientRecordId: Long,
    val amount: Int,
)
