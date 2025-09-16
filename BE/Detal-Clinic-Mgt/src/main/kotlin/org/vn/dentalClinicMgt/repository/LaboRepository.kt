package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.Labo

@Repository
interface LaboRepository : JpaRepository<Labo, Long> {

    @Query(
        """
            SELECT l from Labo l
            WHERE (:search IS NULL 
                    OR LOWER(l.laboName) LIKE LOWER(CONCAT('%', :search,'%'))
                    OR LOWER(l.laboName) LIKE LOWER(CONCAT('%', :search,'%')))                               
        """
    )
    fun getListLabo(
        @Param("search") search: String?,
        pageable: Pageable
    ) : Page<Labo>


    fun existsByLaboName(laboName : String) : Boolean

    fun existsByPhone(phone : String) : Boolean
}