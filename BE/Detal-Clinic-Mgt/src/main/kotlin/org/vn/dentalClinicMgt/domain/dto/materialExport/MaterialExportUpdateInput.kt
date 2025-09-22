package org.vn.dentalClinicMgt.domain.dto.materialExport

data class MaterialExportUpdateInput(
    val materialExportId: Long,
    val materialId: Long,
    val patientRecordId: Long,
    val amount: Int,
)
