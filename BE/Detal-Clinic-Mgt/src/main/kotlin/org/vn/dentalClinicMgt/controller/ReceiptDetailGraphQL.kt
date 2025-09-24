package org.vn.dentalClinicMgt.controller

import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.receiptDetail.ReceiptDetailPage
import org.vn.dentalClinicMgt.service.ReceiptDetailService

@Controller
class ReceiptDetailQuery(
    private val receiptDetailService: ReceiptDetailService
){
    @QueryMapping
    fun getReceiptDetailPage(
        @Argument page: Int,
        @Argument size: Int,
        @Argument search: String?
    ): ReceiptDetailPage{

        val pageable = PageRequest.of(page, size)
        val result = receiptDetailService.getReceiptDetailPage(search, pageable)

        return ReceiptDetailPage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageNumber = result.number,
            pageSize = result.size
        )
    }

    @QueryMapping
    fun getReceiptDetailByReceiptId(
        @Argument page: Int,
        @Argument size: Int,
        @Argument receiptId: Long
    ): ReceiptDetailPage{
        val pageable = PageRequest.of(page, size)
        val result = receiptDetailService.getReceiptDetailByReceiptId(receiptId, pageable)

        return ReceiptDetailPage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageSize = result.size,
            pageNumber = result.number
        )
    }
}