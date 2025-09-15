package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "treatmentservicemap")
data class TreatmentServiceMap(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val treatmentServiceMapId: Long = 0,

    @ManyToOne @JoinColumn(name = "treatmentId")
    val treatment: Treatment? = null,

    @ManyToOne @JoinColumn(name = "serviceId")
    val service: Service? = null,

    val currentPrice: Int,
    val discount: Int?,

    @ManyToOne @JoinColumn(name = "startPatientRecordId")
    val startRecord: PatientRecord? = null
)
