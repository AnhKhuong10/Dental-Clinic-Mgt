package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import org.vn.dentalClinicMgt.utils.constants.PatientRecordServiceStatus

@Entity
@Table(name = "patientrecordservicemap")
data class PatientRecordServiceMap(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val patientRecordServiceMapId: Long = 0,

    @ManyToOne @JoinColumn(name = "patientRecordId")
    val patientRecord: PatientRecord,

    @ManyToOne @JoinColumn(name = "serviceId")
    val service: Service,

    @Enumerated(EnumType.STRING)
    var status: PatientRecordServiceStatus,

    var isPaid: Boolean = false
)

