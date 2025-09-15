package org.vn.dentalClinicMgt.domain.dto.schedule

import org.vn.dentalClinicMgt.utils.constants.ScheduleStatus
import java.time.LocalDate

data class ScheduleCreateInput(
    val patientId: Long,
    val date: LocalDate,
    val status: ScheduleStatus,
    val booked: Boolean,
)
