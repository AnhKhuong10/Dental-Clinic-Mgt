package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "materials")
data class Material(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val materialId: Long = 0,

    val materialName: String?,
    val unit: String?,
    val amount: Int?,
    val price: Int?
)
