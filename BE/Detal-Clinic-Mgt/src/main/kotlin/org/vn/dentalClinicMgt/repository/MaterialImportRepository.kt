package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.MaterialImport
import java.time.LocalDate

@Repository
interface MaterialImportRepository : JpaRepository<MaterialImport, Long> {

    @Query(
        """
            SELECT m FROM MaterialImport m
            join m.material ma
            WHERE m.isDeleted = false 
                AND(:search IS NULL 
                   OR LOWER(m.supplyName) LIKE LOWER(CONCAT('%',:search,'%'))
                    OR LOWER(ma.materialName) LIKE LOWER(CONCAT('%',:search,'%')))
                AND (:fromDate IS NULL OR m.date >= :fromDate)
                AND (:toDate IS NULL OR m.date <= :toDate)
        """
    )
    fun getListMaterialsImport(
        @Param("search") search: String?,
        @Param("fromDate") fromDate: LocalDate?,
        @Param("toDate") toDate: LocalDate?,
        pageable: Pageable
    ) : Page<MaterialImport>

}