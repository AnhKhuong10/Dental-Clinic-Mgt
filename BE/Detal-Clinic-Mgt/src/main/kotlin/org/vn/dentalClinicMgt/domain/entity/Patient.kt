package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import org.vn.dentalClinicMgt.utils.constants.GenderEnum
import org.vn.dentalClinicMgt.utils.constants.PatientStatus
import java.time.LocalDate

@Entity
@Table(name = "patients")
data class Patient(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val patientId: Long = 0,

    @Column(nullable = false)
    var patientName: String,

    var birthdate: LocalDate?,
    @Enumerated(EnumType.STRING) // Lưu enum dạng text
    var gender: GenderEnum? = null,
    var address: String?,
    var phone: String,
    var email: String?,
    var bodyPrehistory: String?,
    var teethPrehistory: String?,
    @Enumerated(EnumType.STRING)
    var status: PatientStatus,
    var isDeleted: Boolean = false,

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    val treatments: MutableList<Treatment> = mutableListOf(),

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val schedules: MutableList<Schedule> = mutableListOf()
)
