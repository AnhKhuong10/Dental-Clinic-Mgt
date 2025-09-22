package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "materialimport")
data class MaterialImport(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val materialImportId: Long = 0,

    @ManyToOne @JoinColumn(name = "materialId")
    var material: Material,

    var date: LocalDate,
    var amount: Int,
    var supplyName: String,
    var price: Int,
    var isDeleted: Boolean = false
)

