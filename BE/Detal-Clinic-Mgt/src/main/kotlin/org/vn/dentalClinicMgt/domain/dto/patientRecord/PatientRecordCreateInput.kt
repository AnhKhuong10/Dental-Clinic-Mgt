package org.vn.dentalClinicMgt.domain.dto.patientRecord

import java.time.LocalDate

data class PatientRecordCreateInput(
    val reason: String,
    val diagnostic: String,
    val causal: String,
    val date: LocalDate,
    val treatmentDescription: String,
    val marrowRecord: String,
    val debit: Int,
    val note: String,
    val treatmentId: Long,
    val userId: Long,
    val prescription: String,
    )
