package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "categoryservice")
data class CategoryService(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val categoryServiceId: Long = 0,

    @Column(nullable = false)
    var categoryServiceName: String
)