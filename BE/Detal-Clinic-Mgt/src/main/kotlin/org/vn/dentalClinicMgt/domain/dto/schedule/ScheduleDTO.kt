package org.vn.dentalClinicMgt.domain.dto.schedule

import org.vn.dentalClinicMgt.utils.constants.ScheduleStatus
import java.time.LocalDate

data class ScheduleDTO(
    val scheduleId: Long,
    val patientName: String,
    val date: LocalDate,
    val status: ScheduleStatus,
)
