package org.vn.dentalClinicMgt.domain.dto.schedule

import org.vn.dentalClinicMgt.domain.entity.Schedule

data class SchedulePage(
    val content: List<Schedule>,
    val totalPages: Int,
    val totalElements: Long,
    val pageSize: Int,
    val pageNumber: Int,
)
