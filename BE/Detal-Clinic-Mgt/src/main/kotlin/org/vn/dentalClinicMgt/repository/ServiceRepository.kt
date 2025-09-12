package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.Service

@Repository
interface ServiceRepository : JpaRepository<Service, Long> {

    @Query("""
        SELECT s FROM Service s
        WHERE (:search IS NULL OR LOWER(s.serviceName) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (:minPrice IS NULL OR s.price >= :minPrice)
          AND (:maxPrice IS NULL OR s.price <= :maxPrice)
    """)
    fun listServicePage(
        @Param("search") search: String?,
        @Param("minPrice") minPrice: Int?,
        @Param("maxPrice") maxPrice: Int?,
        pageable: Pageable
    ): Page<Service>
}