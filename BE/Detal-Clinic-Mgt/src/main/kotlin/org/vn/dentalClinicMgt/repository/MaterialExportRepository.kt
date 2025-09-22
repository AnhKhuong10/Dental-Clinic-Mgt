package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.MaterialExport
import java.time.LocalDate

@Repository
interface MaterialExportRepository : JpaRepository<MaterialExport, Long> {

    @Query(
        """
            SELECT me from MaterialExport me
            join me.material m
            join me.patientRecord pr
            join pr.treatment t
            join t.patient p
            WHERE (:showDeleted = true OR me.isDeleted = false)
            AND(:search IS NULL 
                OR LOWER(m.materialName) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(p.patientName) LIKE LOWER(CONCAT('%', :search, '%')))
           AND (
                (:fromDate IS NULL OR pr.date >= :fromDate)
            )
            AND (
                (:toDate IS NULL OR pr.date <= :toDate)
            )
        """
    )
    fun getMaterialExportPage(
        @Param("search") search: String?,
        @Param("fromDate") fromDate: LocalDate?,
        @Param("toDate") toDate: LocalDate?,
        @Param("showDeleted") showDeleted: Boolean,
        page: Pageable,
    ) : Page<MaterialExport>

}