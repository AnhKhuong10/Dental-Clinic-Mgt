package org.vn.dentalClinicMgt.domain.dto.patient

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.*
import org.vn.dentalClinicMgt.utils.constants.GenderEnum
import java.time.LocalDate


data class PatientCreateInput(
    @field:NotBlank(message = "Patient name is required")
    val patientName: String,

    @field:NotNull(message = "BirthDate is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthdate: LocalDate,

    @field:NotNull(message = "Gender is required")
    val gender: GenderEnum,

    @field:NotBlank(message = "Address is required")
    val address: String,

    @field:Pattern(
        regexp = "^(0[0-9]{9})$",
        message = "Phone number must be 10 digits starting with 0"
    )
    @field:NotBlank(message = "Phone number must not be blank")
    val phone: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email is invalid")
    val email: String,

    val bodyPrehistory: String? = null,
    val teethPrehistory: String? = null,
)