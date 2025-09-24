package org.vn.dentalClinicMgt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
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

    @Query("""
    SELECT tsm 
    FROM TreatmentServiceMap tsm 
    WHERE tsm.treatment.treatmentId = :treatmentId 
      AND tsm.service.serviceId = :serviceId
""")
    fun findByTreatmentIdAndServiceId(
        @Param("treatmentId") treatmentId: Long,
        @Param("serviceId") serviceId: Long
    ): TreatmentServiceMap?

    fun existsByTreatmentTreatmentIdAndServiceServiceId(treatmentId: Long, serviceId: Long): Boolean
}