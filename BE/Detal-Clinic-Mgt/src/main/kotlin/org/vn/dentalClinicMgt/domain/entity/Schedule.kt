package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import org.vn.dentalClinicMgt.utils.constants.ScheduleStatus
import java.time.LocalDate

@Entity
@Table(name = "schedule")
data class Schedule(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val scheduleId: Long = 0,

    @ManyToOne @JoinColumn(name = "patientId")
    var patient: Patient,

    var date: LocalDate,

    @Enumerated(EnumType.STRING)
    var status: ScheduleStatus,
    var booked: Boolean = false
)