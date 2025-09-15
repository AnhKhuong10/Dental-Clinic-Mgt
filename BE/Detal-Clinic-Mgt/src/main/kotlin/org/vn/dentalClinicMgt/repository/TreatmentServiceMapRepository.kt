package org.vn.dentalClinicMgt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.TreatmentServiceMap

@Repository
interface TreatmentServiceMapRepository : JpaRepository<TreatmentServiceMap, Long> {

    @Query("""
        SELECT m 
        FROM TreatmentServiceMap m
        JOIN FETCH m.service s
        WHERE m.treatment.treatmentId = :treatmentId
    """)
    fun findByTreatmentId(treatmentId: Long): List<TreatmentServiceMap>
}