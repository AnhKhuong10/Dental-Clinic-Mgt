package org.vn.dentalClinicMgt.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.Receipt
import org.vn.dentalClinicMgt.domain.entity.Treatment
import org.vn.dentalClinicMgt.utils.constants.PaymentType
import java.time.LocalDate

@Repository
interface ReceiptRepository : JpaRepository<Receipt, Long> {
    fun existsByTreatmentAndPaymentType(treatment: Treatment, paymentType: PaymentType): Boolean

    @Query("SELECT COALESCE(SUM(r.debit), 0) FROM Receipt r WHERE r.treatment = :treatment")
    fun sumDebitByTreatment(treatment: Treatment): Int

    @Query("""
        SELECT r FROM Receipt r
        join r.treatment t 
        join t.patient p
        join t.patientRecords pr
        WHERE (:search IS NULL
                OR lower(pr.treatmentDescription) like lower(concat('%', :search,'%'))
                OR lower(p.patientName) like lower(concat('%', :search,'%'))
                OR lower(p.phone) like lower(concat('%', :search,'%')))   
        AND(:paymentType IS NULL OR r.paymentType = :paymentType) 
        AND (
                (:fromDate IS NULL OR pr.date >= :fromDate)
            )
        AND (
                (:toDate IS NULL OR pr.date <= :toDate)
            )
    """)
    fun getListReceipt(
        @Param("search") search: String?,
        @Param("fromDate") fromDate: LocalDate?,
        @Param("toDate") toDate: LocalDate?,
        @Param("paymentType") paymentType: PaymentType?,
        pageable: Pageable
    ): Page<Receipt>
}