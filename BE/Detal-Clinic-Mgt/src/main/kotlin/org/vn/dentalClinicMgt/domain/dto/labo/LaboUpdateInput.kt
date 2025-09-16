package org.vn.dentalClinicMgt.domain.dto.labo

data class LaboUpdateInput(
    val laboId: Long,
    val laboName: String,
    val phone: String,
)
