package org.vn.dentalClinicMgt.domain.dto.user

import java.time.LocalDate

data class UpdateUserInput(
    val userId: Long,
    val fullName: String?,
    val birthdate: LocalDate,
    val phone: String?,
    val email: String?,
    val salary: Int?,
    val enabled: Boolean?,
    val roleId: Long?
)