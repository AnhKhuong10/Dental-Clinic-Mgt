package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "material_import")
data class MaterialImport(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val materialImportId: Long = 0,

    @ManyToOne @JoinColumn(name = "materialId")
    val material: Material? = null,

    val date: LocalDate?,
    val amount: Int?,
    val supplyName: String?,
    val totalPrice: Int?,
    val isDeleted: Boolean = false
)

