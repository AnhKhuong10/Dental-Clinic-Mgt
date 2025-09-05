package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "services")
data class Service(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val serviceId: Long = 0,

    @Column(nullable = false)
    val serviceName: String,

    val price: Int,
    val marketPrice: Int?,

    @ManyToOne @JoinColumn(name = "categoryServiceId")
    val categoryService: CategoryService? = null
)