package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.Patient
import org.vn.dentalClinicMgt.utils.constants.GenderEnum
import org.vn.dentalClinicMgt.utils.constants.PatientStatus

@Repository
interface PatientRepository : JpaRepository<Patient, Long> {

    @Query("""
       SELECT p FROM Patient p
            WHERE (:search IS NULL 
                      OR LOWER(p.patientName) LIKE LOWER(CONCAT('%', :search, '%'))
                      OR LOWER(p.address) LIKE LOWER(CONCAT('%', :search, '%'))
                      OR LOWER(p.phone) LIKE LOWER(CONCAT('%', :search, '%'))
                      OR LOWER(p.email) LIKE LOWER(CONCAT('%', :search, '%'))
                      OR LOWER(p.bodyPrehistory) LIKE LOWER(CONCAT('%', :search, '%'))
                      OR LOWER(p.teethPrehistory) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:gender IS NULL OR p.gender = :gender)
              AND (:status IS NULL OR p.status = :status)
    """)

    fun listPatients(
        @Param("search") search: String?,
        @Param("gender") gender: GenderEnum?,
        @Param("status") status: PatientStatus?,
        pageable: Pageable,
        ): Page<Patient>
}