package org.vn.dentalClinicMgt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.Treatment

@Repository
interface TreatmentRepository : JpaRepository<Treatment, Long> {

    @Query("""
        SELECT t 
        FROM Treatment t
        WHERE t.patient.patientId = :patientId
    """)
    fun findByPatientId(patientId: Long): List<Treatment>
}