package org.vn.dentalClinicMgt.controller

import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.receipt.ReceiptCreateInput
import org.vn.dentalClinicMgt.domain.dto.receipt.ReceiptDTO
import org.vn.dentalClinicMgt.domain.dto.receipt.ReceiptPage
import org.vn.dentalClinicMgt.service.ReceiptService
import org.vn.dentalClinicMgt.utils.constants.PaymentType
import java.time.LocalDate

@Controller
class ReceiptQuery(
    private val receiptService: ReceiptService
){

    @QueryMapping
    fun getReceiptPage(
        @Argument page: Int,
        @Argument size: Int,
        @Argument search: String?,
        @Argument fromDate: LocalDate?,
        @Argument toDate: LocalDate?,
        @Argument paymentType: PaymentType?
    ) : ReceiptPage{

        val pageable = PageRequest.of(page, size)

        val result = receiptService.getListReceipts(search, fromDate, toDate, paymentType, pageable)
        return ReceiptPage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageSize = result.size,
            pageNumber = result.number
        )
    }

    @QueryMapping
    fun getReceiptDetail(@Argument id: Long): ReceiptDTO{
        return receiptService.getReceiptDetail(id)
    }
}



@Controller
class ReceiptMutation(
    private val receiptService: ReceiptService
){
    @MutationMapping
    fun createReceipt(@Argument input: ReceiptCreateInput) : ReceiptDTO {

        return receiptService.createReceipt(input)
    }
}