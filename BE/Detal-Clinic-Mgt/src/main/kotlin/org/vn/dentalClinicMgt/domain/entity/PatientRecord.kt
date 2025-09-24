package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "patientrecords")
data class PatientRecord(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val patientRecordId: Long = 0,

    var reason: String,
    var diagnostic: String,
    var causal: String,
    var date: LocalDate,
    var treatmentDescription: String,
    var marrowRecord: String,
    var debit: Int,
    var note: String,
    var prescription: String,

    @ManyToOne @JoinColumn(name = "treatmentId")
    var treatment: Treatment,
    @ManyToOne @JoinColumn(name = "userId")
    var user: User,

    @OneToMany(mappedBy = "patientRecord", cascade = [CascadeType.ALL])
    val materialExport: List<MaterialExport> = mutableListOf(),

    @OneToMany(mappedBy = "patientRecord", cascade = [CascadeType.ALL])
    val patientRecordServiceMap: List<PatientRecordServiceMap> = mutableListOf(),

    @OneToMany(mappedBy = "patientRecord", cascade = [CascadeType.ALL])
    val receipts: List<Receipt> = mutableListOf()
)
