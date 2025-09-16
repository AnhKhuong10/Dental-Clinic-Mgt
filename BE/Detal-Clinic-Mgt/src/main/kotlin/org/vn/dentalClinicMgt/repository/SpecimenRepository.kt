package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.Specimen
import java.time.LocalDate

@Repository
interface SpecimenRepository : JpaRepository<Specimen, Long> {


    @Query(
        """
            SELECT s from Specimen s
            join s.labo l
            join s.patientRecord pr
            join pr.treatment t
            join t.patient p
            WHERE(:search IS NULL 
                    OR LOWER(l.laboName) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(p.patientName) LIKE LOWER(CONCAT('%', :search, '%')))
            AND (
                (:fromDate IS NULL OR s.receiveDate >= :fromDate OR s.deliveryDate >= :fromDate)
            )
            AND (
                (:toDate IS NULL OR s.receiveDate <= :toDate OR s.deliveryDate <= :toDate)
            )

        """
    )
    fun getListSpecimens(
        @Param("search") search: String?,
        @Param("fromDate") fromDate: LocalDate?,
        @Param("toDate") toDate: LocalDate?,
        pageable: Pageable,
    ) : Page<Specimen>
}