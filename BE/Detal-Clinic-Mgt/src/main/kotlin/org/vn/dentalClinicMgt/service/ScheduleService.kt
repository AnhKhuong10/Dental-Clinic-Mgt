package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.schedule.ScheduleCreateInput
import org.vn.dentalClinicMgt.domain.dto.schedule.ScheduleDTO
import org.vn.dentalClinicMgt.domain.dto.schedule.ScheduleUpdateInput
import org.vn.dentalClinicMgt.domain.entity.Schedule
import org.vn.dentalClinicMgt.utils.constants.ScheduleStatus
import java.time.LocalDate

interface ScheduleService {

    fun getSchedulePage(search: String?, fromDate: LocalDate?, toDate: LocalDate?, status: ScheduleStatus?,
                        pageable: Pageable): Page<Schedule>

    fun createSchedule(input: ScheduleCreateInput): ScheduleDTO

    fun updateSchedule(input: ScheduleUpdateInput): ScheduleDTO

    fun joinSchedule(scheduleId: Long): ScheduleDTO   // WAITING -> TREATING

    fun completeSchedule(scheduleId: Long): ScheduleDTO  // TREATING -> COMPLETED

    fun cancelSchedule(scheduleId: Long): ScheduleDTO    // bất kỳ -> CANCELLED
}