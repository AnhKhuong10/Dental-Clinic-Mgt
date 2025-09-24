package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.ReceiptDetail

@Repository
interface ReceiptDetailRepository : JpaRepository<ReceiptDetail, Long> {

    @Query("""
    SELECT rd FROM ReceiptDetail rd
    WHERE (:search IS NULL 
            OR lower(rd.itemName) LIKE lower(concat('%', :search, '%'))
            OR lower(rd.itemType) LIKE lower(concat('%', :search, '%')))
""")
    fun getReceiptDetailPage(
        @Param("search") search: String?,
        pageable: Pageable
    ): Page<ReceiptDetail>

    @Query(
        """
            SELECT rd from ReceiptDetail rd WHERE rd.receipt.receiptId = :receiptId
        """
    )
    fun getReceiptDetailByReceiptId(@Param("receiptId") receiptId: Long, pageable: Pageable): Page<ReceiptDetail>
}