package org.vn.dentalClinicMgt.controller

import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.schedule.ScheduleCreateInput
import org.vn.dentalClinicMgt.domain.dto.schedule.ScheduleDTO
import org.vn.dentalClinicMgt.domain.dto.schedule.SchedulePage
import org.vn.dentalClinicMgt.domain.dto.schedule.ScheduleUpdateInput
import org.vn.dentalClinicMgt.service.ScheduleService
import org.vn.dentalClinicMgt.utils.constants.ScheduleStatus
import java.time.LocalDate

@Controller
class ScheduleQuery(
    val scheduleService: ScheduleService
){

    @PreAuthorize("hasRole('SCHEDULE_VIEW')")
    @QueryMapping
    fun getSchedulePage(
        @Argument page: Int,
        @Argument size: Int,
        @Argument search: String?,
        @Argument fromDate: LocalDate?,
        @Argument toDate: LocalDate?,
        @Argument status: ScheduleStatus?
    ) : SchedulePage {

        val pageable = PageRequest.of(page, size)
        val result = scheduleService.getSchedulePage(search, fromDate, toDate, status, pageable)

        return SchedulePage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageNumber = result.number,
            pageSize = result.size
        )
    }
}

@Controller
class ScheduleMutation(
    val scheduleService: ScheduleService
){

    @MutationMapping
    fun createSchedule(@Argument input: ScheduleCreateInput): ScheduleDTO {
        return scheduleService.createSchedule(input)
    }

    @MutationMapping
    fun updateSchedule(@Argument input: ScheduleUpdateInput): ScheduleDTO {
        return scheduleService.updateSchedule(input)
    }

    @MutationMapping
    fun joinSchedule(@Argument scheduleId: Long): ScheduleDTO {
        return scheduleService.joinSchedule(scheduleId)
    }

    @MutationMapping
    fun completeSchedule(@Argument scheduleId: Long): ScheduleDTO {
        return scheduleService.completeSchedule(scheduleId)
    }

    @MutationMapping
    fun cancelSchedule(@Argument scheduleId: Long): ScheduleDTO {
        return scheduleService.cancelSchedule(scheduleId)
    }

}