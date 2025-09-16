package org.vn.dentalClinicMgt.domain.dto.material

data class MaterialUpdateInput(
    val materialId: Long,

    val materialName: String,
    val unit: String,
    val amount: Int,
    val price: Int
)
