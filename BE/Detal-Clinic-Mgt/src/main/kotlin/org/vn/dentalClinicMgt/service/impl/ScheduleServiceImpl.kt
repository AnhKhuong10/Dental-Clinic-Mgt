package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.schedule.ScheduleCreateInput
import org.vn.dentalClinicMgt.domain.dto.schedule.ScheduleDTO
import org.vn.dentalClinicMgt.domain.dto.schedule.ScheduleUpdateInput
import org.vn.dentalClinicMgt.domain.entity.Schedule
import org.vn.dentalClinicMgt.repository.PatientRepository
import org.vn.dentalClinicMgt.repository.ScheduleRepository
import org.vn.dentalClinicMgt.service.ScheduleService
import org.vn.dentalClinicMgt.utils.constants.ScheduleStatus
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode
import java.time.LocalDate

@Service
class ScheduleServiceImpl(
    private val scheduleRepository: ScheduleRepository,
    private val patientRepository: PatientRepository,
): ScheduleService {
    override fun getSchedulePage(
        search: String?,
        fromDate: LocalDate?,
        toDate: LocalDate?,
        status: ScheduleStatus?,
        pageable: Pageable
    ): Page<Schedule> {
        return scheduleRepository.listSchedules(search, fromDate, toDate, status, pageable)
    }

    override fun createSchedule(input: ScheduleCreateInput): ScheduleDTO {

        val patient = patientRepository.findById(input.patientId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "Patient with id ${input.patientId} not found") }

        val schedule = Schedule(
            patient = patient,
            date = input.date,
            status = input.status,
            booked = input.booked,
        )
        return scheduleRepository.save(schedule).toScheduleDTO()
    }

    override fun updateSchedule(input: ScheduleUpdateInput): ScheduleDTO {
        val schedule = scheduleRepository.findById(input.scheduleId)
            .orElseThrow{ BusinessException(ErrorCode.NOT_FOUND, "Schedule with id ${input.scheduleId} not found") }

        val patient = patientRepository.findById(input.patientId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "Patient with id ${input.patientId} not found") }

        schedule.patient = patient
        schedule.date = input.date
        schedule.status = input.status
        schedule.booked = input.booked
        return scheduleRepository.save(schedule).toScheduleDTO()
    }

    override fun joinSchedule(scheduleId: Long): ScheduleDTO {
        val schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow{ BusinessException(ErrorCode.NOT_FOUND, "Schedule with id $scheduleId not found") }

        if (schedule.status != ScheduleStatus.WAITING) {
            throw BusinessException(ErrorCode.BAD_REQUEST, "Only schedules in WAITING can be called")
        }

        schedule.status = ScheduleStatus.TREATING
        return scheduleRepository.save(schedule).toScheduleDTO()
    }

    override fun completeSchedule(scheduleId: Long): ScheduleDTO {
        val schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow{ BusinessException(ErrorCode.NOT_FOUND, "Schedule with id $scheduleId not found") }

        if (schedule.status != ScheduleStatus.TREATING) {
            throw BusinessException(ErrorCode.BAD_REQUEST, "Only schedules in TREATING can be called")
        }

        schedule.status = ScheduleStatus.COMPLETED
        return scheduleRepository.save(schedule).toScheduleDTO()
    }

    override fun cancelSchedule(scheduleId: Long): ScheduleDTO {
        val schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow{ BusinessException(ErrorCode.NOT_FOUND, "Schedule with id $scheduleId not found") }

        schedule.status = ScheduleStatus.CANCELLED
        return scheduleRepository.save(schedule).toScheduleDTO()
    }

    private fun Schedule.toScheduleDTO() = ScheduleDTO(
        scheduleId = this.scheduleId,
        patientName = this.patient.patientName,
        date = this.date,
        status = this.status,
    )
}
