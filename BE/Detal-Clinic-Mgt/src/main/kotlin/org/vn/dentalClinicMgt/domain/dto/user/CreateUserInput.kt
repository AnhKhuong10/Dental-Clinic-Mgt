package org.vn.dentalClinicMgt.domain.dto.user

import jakarta.validation.constraints.*
import java.time.LocalDate

data class CreateUserInput(
    @field:NotBlank(message = "Username must not be blank")
    val username: String,

    @field:NotBlank(message = "Password must not be blank")
    val password: String,

    @field:NotBlank(message = "RePassword must not be blank")
    val rePassword: String,

    @field:NotBlank(message = "Full name must not be blank")
    val fullName: String,

    val birthdate: LocalDate,

    @field:Pattern(regexp = "\\d{10,12}", message = "Phone must be 10–12 digits")
    val phone: String,

    @field:Email(message = "Invalid email format")
    val email: String,

    @field:Min(value = 0, message = "Salary must be positive")
    val salary: Int?,

    val enabled: Boolean = true,

    @field:NotNull(message = "RoleId is required")
    val roleId: Long
)
