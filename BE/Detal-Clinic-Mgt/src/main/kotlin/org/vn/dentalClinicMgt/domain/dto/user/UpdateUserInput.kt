package org.vn.dentalClinicMgt.domain.dto.user

import jakarta.validation.constraints.*
import java.time.LocalDate

data class UpdateUserInput(
    val userId: Long,

    @field:NotBlank(message = "FullName must not be blank")
    val fullName: String,
    val birthdate: LocalDate,

    @field:NotBlank(message = "Phone must not be blank")
    val phone: String,

    @field:Min(value = 0, message = "Salary must be positive")
    val salary: Int?,
    val enabled: Boolean = true,

    @field:NotNull(message = "RoleId is required")
    val roleId: Long
)