package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.receiptDetail.ReceiptDetailDTO
import org.vn.dentalClinicMgt.domain.entity.ReceiptDetail
import org.vn.dentalClinicMgt.repository.ReceiptDetailRepository
import org.vn.dentalClinicMgt.repository.ReceiptRepository
import org.vn.dentalClinicMgt.service.ReceiptDetailService
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode

@Service
class ReceiptDetailServiceImpl (
    private val receiptDetailRepository: ReceiptDetailRepository,
    private val receiptRepository: ReceiptRepository,
) : ReceiptDetailService {

    override fun getReceiptDetailPage(search: String?, pageable: Pageable): Page<ReceiptDetailDTO> {
        val receiptDetails = receiptDetailRepository.getReceiptDetailPage(search, pageable)
        print("search: $search")
        receiptDetails.content.forEach {
            println(it.toDTO())   // hoặc println(it.toDTO()) nếu muốn in DTO
        }

        return receiptDetails.map { it.toDTO() }
    }

    override fun getReceiptDetailByReceiptId(receiptId: Long, pageable: Pageable): Page<ReceiptDetailDTO> {
        val receipt = receiptRepository.findById(receiptId).orElseThrow {
            BusinessException(ErrorCode.NOT_FOUND, "receipt not found")
        }

        val receiptDetail = receiptDetailRepository.getReceiptDetailByReceiptId(receipt.receiptId, pageable)
        if(receiptDetail.isEmpty){
            throw BusinessException(ErrorCode.NOT_FOUND, "receipt not found")
        }
        return receiptDetail.map { it.toDTO() }
    }

    private fun ReceiptDetail.toDTO() = ReceiptDetailDTO(
        receiptDetailId = this.receiptDetailId,
        receiptId = this.receipt.receiptId,
        itemType = this.itemType,
        itemName = this.itemName,
        quantity = this.quantity,
        unitPrice = this.unitPrice,
        totalPrice = this.totalPrice,
    )
}