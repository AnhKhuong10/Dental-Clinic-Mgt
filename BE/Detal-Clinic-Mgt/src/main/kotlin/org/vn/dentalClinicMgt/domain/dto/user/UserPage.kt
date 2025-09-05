package org.vn.dentalClinicMgt.domain.dto.user

import org.vn.dentalClinicMgt.domain.entity.User

data class UserPage(
    val content: List<User>,
    val totalPages: Int,
    val totalElements: Long,
    val pageNumber: Int,
    val pageSize: Int
)
