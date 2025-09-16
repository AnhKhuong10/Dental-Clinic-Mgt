package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.Material

@Repository
interface MaterialRepository : JpaRepository<Material, Long> {

    @Query(
        """
            SELECT m from Material m
            WHERE (:search IS NULL 
            OR LOWER(m.materialName) LIKE CONCAT('%', :search, '%') 
            OR LOWER(m.unit) LIKE CONCAT('%', :search, '%'))
        """
    )
    fun getListMaterials(
        @Param("search") search: String?,
        page: Pageable
    ): Page<Material>

    fun existsMaterialsByMaterialName( name: String): Boolean
}