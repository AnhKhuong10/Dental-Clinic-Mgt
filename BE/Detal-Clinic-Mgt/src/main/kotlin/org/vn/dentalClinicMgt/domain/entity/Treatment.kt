package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "treatments")
data class Treatment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val treatmentId: Long = 0,

    @ManyToOne @JoinColumn(name = "patientId")
    val patient: Patient,

    @OneToMany(mappedBy = "treatment", fetch = FetchType.LAZY)
    val receipts: MutableList<Receipt> = mutableListOf(),

    @OneToMany(mappedBy = "treatment", fetch = FetchType.LAZY)
    val patientRecords: MutableList<PatientRecord> = mutableListOf(),
)