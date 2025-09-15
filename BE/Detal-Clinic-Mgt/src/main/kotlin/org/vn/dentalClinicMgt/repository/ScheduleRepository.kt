package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.Schedule
import org.vn.dentalClinicMgt.utils.constants.ScheduleStatus
import java.time.LocalDate

@Repository
interface ScheduleRepository : JpaRepository<Schedule, Long> {

    @Query("""
        SELECT s 
        FROM Schedule s 
        JOIN s.patient p
        WHERE 
            (:search IS NULL 
             OR LOWER(p.patientName) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:fromDate IS NULL OR s.date >= :fromDate)
        AND (:toDate IS NULL OR s.date <= :toDate)
        AND (:status IS NULL OR s.status = :status)
    """)
    fun listSchedules(
        @Param("search") search: String?,
        @Param("fromDate") fromDate: LocalDate?,
        @Param("toDate") toDate: LocalDate?,
        @Param("status") status: ScheduleStatus?,
        pageable: Pageable
    ): Page<Schedule>
}