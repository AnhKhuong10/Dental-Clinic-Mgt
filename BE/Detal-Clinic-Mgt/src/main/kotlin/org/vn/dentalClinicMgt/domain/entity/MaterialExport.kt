package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "material_export")
data class MaterialExport(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val materialExportId: Long = 0,

    @ManyToOne @JoinColumn(name = "materialId")
    val material: Material? = null,

    @ManyToOne @JoinColumn(name = "patientRecordId")
    val patientRecord: PatientRecord? = null,

    val amount: Int?,
    val totalPrice: Int?,
    val isDeleted: Boolean = false
)
