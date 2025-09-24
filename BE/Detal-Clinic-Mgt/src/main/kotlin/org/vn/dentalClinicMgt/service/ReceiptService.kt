package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.receipt.ReceiptCreateInput
import org.vn.dentalClinicMgt.domain.dto.receipt.ReceiptDTO
import org.vn.dentalClinicMgt.utils.constants.PaymentType
import java.time.LocalDate

interface ReceiptService {

    fun createReceipt(input: ReceiptCreateInput): ReceiptDTO

    fun getListReceipts(search: String?,
                        fromDate: LocalDate?,
                        toDate: LocalDate?,
                        paymentType: PaymentType?,
                        page: Pageable): Page<ReceiptDTO>

    fun getReceiptDetail(receiptId : Long): ReceiptDTO
}