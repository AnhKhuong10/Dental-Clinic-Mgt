package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "specimens")
data class Specimen(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val specimenId: Long = 0,

    val specimenName: String?,
    val receiveDate: LocalDate?,
    val deliveryDate: LocalDate?,
    val amount: Int?,
    val price: Int?,

    @ManyToOne @JoinColumn(name = "patientRecordId")
    val patientRecord: PatientRecord? = null,

    @ManyToOne @JoinColumn(name = "laboId")
    val labo: Labo? = null
)
