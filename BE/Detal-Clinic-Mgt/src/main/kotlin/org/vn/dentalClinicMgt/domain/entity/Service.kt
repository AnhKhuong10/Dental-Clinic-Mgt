package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "services")
data class Service(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val serviceId: Long = 0,

    @Column(nullable = false)
    var serviceName: String,

    var price: Int,
    var marketPrice: Int?,

    @ManyToOne @JoinColumn(name = "categoryServiceId")
    var categoryService: CategoryService

)