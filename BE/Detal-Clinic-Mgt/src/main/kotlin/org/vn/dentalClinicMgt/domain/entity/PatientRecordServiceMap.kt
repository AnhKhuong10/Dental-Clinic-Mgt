package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "patient_record_service_map")
data class PatientRecordServiceMap(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val patientRecordServiceMapId: Long = 0,

    @ManyToOne @JoinColumn(name = "patientRecordId")
    val patientRecord: PatientRecord? = null,

    @ManyToOne @JoinColumn(name = "serviceId")
    val service: Service? = null,

    val status: Int?
)

