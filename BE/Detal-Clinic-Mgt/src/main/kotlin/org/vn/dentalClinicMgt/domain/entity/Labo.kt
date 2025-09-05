package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "labos")
data class Labo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val laboId: Long = 0,

    val laboName: String?,
    val phone: String?,
    val isDeleted: Boolean = false
)
