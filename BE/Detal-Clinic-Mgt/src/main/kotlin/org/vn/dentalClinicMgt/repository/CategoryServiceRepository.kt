package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.CategoryService

@Repository
interface CategoryServiceRepository : JpaRepository<CategoryService, Long> {

    @Query("""
        SELECT c FROM CategoryService c
        WHERE (
            :search IS NULL 
            OR LOWER(c.categoryServiceName) LIKE LOWER(CONCAT('%',:search,'%')))
    """)
    fun listCategoryService(
        @Param("search") search: String?,
        pageable: Pageable
    ) : Page<CategoryService>
}