package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "specimens")
data class Specimen(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val specimenId: Long = 0,

    var specimenName: String,
    var receiveDate: LocalDate,
    var deliveryDate: LocalDate,
    var amount: Int,
    var price: Int,

    @ManyToOne @JoinColumn(name = "patientRecordId")
    var patientRecord: PatientRecord,

    @ManyToOne @JoinColumn(name = "laboId")
    var labo: Labo
)
