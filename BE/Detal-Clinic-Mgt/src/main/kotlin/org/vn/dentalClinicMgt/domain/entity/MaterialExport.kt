package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "materialexport")
data class MaterialExport(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val materialExportId: Long = 0,

    @ManyToOne @JoinColumn(name = "materialId")
    var material: Material,

    @ManyToOne @JoinColumn(name = "patientRecordId")
    var patientRecord: PatientRecord,

    var amount: Int,
    var price: Int,
    var isDeleted: Boolean = false
)
