package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "materials")
data class Material(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val materialId: Long = 0,

    var materialName: String,
    var unit: String,
    var amount: Int,
    var price: Int
)
