package org.vn.dentalClinicMgt.domain.dto.material

data class MaterialCreateInput(
    val materialName: String,
    val unit: String,
    val amount: Int,
    val price: Int
)
