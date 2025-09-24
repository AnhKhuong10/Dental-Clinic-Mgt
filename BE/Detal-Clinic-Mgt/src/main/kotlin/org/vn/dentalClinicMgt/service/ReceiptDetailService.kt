package org.vn.dentalClinicMgt.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.vn.dentalClinicMgt.domain.dto.receiptDetail.ReceiptDetailDTO

interface ReceiptDetailService {

    fun getReceiptDetailPage(search :String?, pageable: Pageable): Page<ReceiptDetailDTO>

    fun getReceiptDetailByReceiptId(receiptId: Long, pageable: Pageable) : Page<ReceiptDetailDTO>
}