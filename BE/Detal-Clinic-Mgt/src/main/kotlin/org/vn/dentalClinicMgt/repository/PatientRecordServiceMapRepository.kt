package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.PatientRecordServiceMap

@Repository
interface PatientRecordServiceMapRepository : JpaRepository<PatientRecordServiceMap, Long>{

        @Query(
            """
                SELECT p FROM PatientRecordServiceMap p 
            """
        )
        fun listPatientRecordService(pageable: Pageable): Page<PatientRecordServiceMap>

        @Query(
            """
                SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END 
        FROM PatientRecordServiceMap p 
        WHERE p.patientRecord.patientRecordId = :patientRecordId 
          AND p.service.serviceId = :serviceId
            """
        )
    fun existsByPatientRecordIdAndServiceId(patientRecordId: Long, serviceId: Long): Boolean
}