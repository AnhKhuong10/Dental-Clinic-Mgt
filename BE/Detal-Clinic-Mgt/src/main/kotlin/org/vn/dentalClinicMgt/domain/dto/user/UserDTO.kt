package org.vn.dentalClinicMgt.domain.dto.user

import java.time.LocalDate

data class UserDTO(
    val userId: Long,
    val username: String,
    val fullName: String?,
    val email: String?,
    val phone: String?,
    val birthdate: LocalDate?,
    val salary: Int?,
    val enabled: Boolean,
    val roleName: String
)