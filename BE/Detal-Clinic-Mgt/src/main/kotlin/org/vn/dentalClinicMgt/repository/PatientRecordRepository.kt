package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.PatientRecord
import java.time.LocalDate

@Repository
interface PatientRecordRepository : JpaRepository<PatientRecord, Long> {

    @Query(
        """
            SELECT p FROM PatientRecord p
                WHERE (:search IS NULL
                        OR LOWER(p.reason) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(p.diagnostic) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(p.causal) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(p.treatmentDescription) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(p.marrowRecord) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(p.note) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(p.prescription) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:fromDate IS NULL OR p.date >= :fromDate)
        AND (:toDate IS NULL OR p.date <= :toDate)
        """
    )
    fun listPatientRecords(
        @Param("search") search: String?,
        @Param("fromDate") fromDate: LocalDate?,
        @Param("toDate") toDate: LocalDate?,
        pageable: Pageable,
        ): Page<PatientRecord>
}