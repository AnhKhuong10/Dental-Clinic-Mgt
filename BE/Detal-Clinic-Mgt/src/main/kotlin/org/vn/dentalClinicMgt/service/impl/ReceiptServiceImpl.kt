package org.vn.dentalClinicMgt.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.dto.receipt.ReceiptCreateInput
import org.vn.dentalClinicMgt.domain.dto.receipt.ReceiptDTO
import org.vn.dentalClinicMgt.domain.entity.PatientRecordServiceMap
import org.vn.dentalClinicMgt.domain.entity.Receipt
import org.vn.dentalClinicMgt.domain.entity.ReceiptDetail
import org.vn.dentalClinicMgt.repository.*
import org.vn.dentalClinicMgt.service.ReceiptService
import org.vn.dentalClinicMgt.utils.constants.PatientRecordServiceStatus
import org.vn.dentalClinicMgt.utils.constants.PaymentType
import org.vn.dentalClinicMgt.utils.exception.BusinessException
import org.vn.dentalClinicMgt.utils.exception.ErrorCode
import java.time.LocalDate

@Service
class ReceiptServiceImpl(
    private val receiptRepository: ReceiptRepository,
    private val treatmentRepository: TreatmentRepository,
    private val materialExportRepository: MaterialExportRepository,
    private val patientRecordRepository: PatientRecordRepository,
    private val patientRecordServiceMapRepository: PatientRecordServiceMapRepository,
    private val treatmentServiceMapRepository: TreatmentServiceMapRepository,
    private val receiptDetailRepository: ReceiptDetailRepository
) : ReceiptService {

    override fun createReceipt(input: ReceiptCreateInput): ReceiptDTO {
        val treatment = treatmentRepository.findById(input.treatmentId)
            .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "Treatment not found!") }

        val patientRecord = input.patientRecordId.let {
            patientRecordRepository.findById(it)
                .orElseThrow { BusinessException(ErrorCode.NOT_FOUND, "Patient record not found!") }
        }

        var price = 0
        var discount = 0
        var totalPrice = 0
        var billableServices: List<PatientRecordServiceMap> = emptyList()

        when (input.paymentType) {

            PaymentType.PER_TREATMENT -> {
                if (receiptRepository.existsByTreatmentAndPaymentType(treatment, PaymentType.PER_TREATMENT)) {
                    throw BusinessException(ErrorCode.BAD_REQUEST, "Treatment already has PER_TREATMENT receipt")
                }

                val services = treatmentServiceMapRepository.findByTreatmentId(treatment.treatmentId)
                price = services.sumOf { it.currentPrice }
                discount = services.sumOf { it.discount }
                totalPrice = price - discount

                // cộng thêm material ở buổi đầu tiên (nếu có patientRecord)
                patientRecord?.let {
                    val materialCost = materialExportRepository.findByPatientRecordId(it.patientRecordId)
                        .sumOf { me -> me.price * me.amount }
                    totalPrice += materialCost
                }
            }

            PaymentType.PER_RECORD -> {
                val hasTreatmentReceipt = receiptRepository.existsByTreatmentAndPaymentType(
                    treatment, PaymentType.PER_TREATMENT
                )
                val services = patientRecordServiceMapRepository.findByPatientRecordId(patientRecord.patientRecordId)
                println("All services: ${services.map { "${it.service.serviceName} - ${it.status} - paid=${it.isPaid}" }}")
                billableServices = if (!hasTreatmentReceipt) {
                    // Trường hợp chưa thanh toán trọn gói
                    services.filter {
                        it.status == PatientRecordServiceStatus.DONE && !it.isPaid
                    }
                } else {
                    // Trường hợp đã thanh toán trọn gói
                    services.filter {
                        it.status == PatientRecordServiceStatus.DONE &&
                                !it.isPaid &&
                                !treatmentServiceMapRepository.existsByTreatmentTreatmentIdAndServiceServiceId(
                                    treatment.treatmentId,
                                    it.service.serviceId
                                )
                    }
                }

                println("Billable services: ${billableServices.map { it.service.serviceName }}")

                val materialCost = materialExportRepository.findByPatientRecordId(patientRecord.patientRecordId)
                    .sumOf { it.price * it.amount }

                if (billableServices.isEmpty() && materialCost == 0) {
                    throw BusinessException(ErrorCode.BAD_REQUEST, "No completed services or materials to bill for this record")
                }

                val servicePrice = billableServices.sumOf { it.service.price }
                discount = billableServices.sumOf {
                    treatmentServiceMapRepository
                        .findByTreatmentIdAndServiceId(treatment.treatmentId, it.service.serviceId)
                        ?.discount ?: 0
                }

                price = servicePrice + materialCost
                totalPrice = price - discount

                // cập nhật đã thanh toán
                billableServices.forEach { it.isPaid = true }
                patientRecordServiceMapRepository.saveAll(billableServices)
            }
        }

        val oldDebit = receiptRepository.sumDebitByTreatment(treatment)

        // tổng cần thanh toán = service + material + nợ cũ
        val totalWithOldDebit = totalPrice + oldDebit
        val rawDebit = totalWithOldDebit - input.payment
        val debit = if (rawDebit < 0) 0 else rawDebit

        patientRecord.debit = debit
        patientRecordRepository.save(patientRecord)

        val receipt = receiptRepository.save(
            Receipt(
                treatment = treatment,
                patientRecord = patientRecord,
                paymentType = input.paymentType,
                price = price,
                discount = discount,
                totalPrice = totalPrice,
                payment = input.payment,
                debit = debit,
                date = LocalDate.now()
            )
        )
        // Lưu chi tiết service
        billableServices.forEach { s ->
            receiptDetailRepository.save(
                ReceiptDetail(
                    receipt = receipt,
                    itemType = "SERVICE",
                    itemName = s.service.serviceName,
                    quantity = 1,
                    unitPrice = s.service.price,
                    totalPrice = s.service.price
                )
            )
        }

    // Lưu chi tiết material
        val materials = materialExportRepository.findByPatientRecordId(patientRecord.patientRecordId)
        materials.forEach { m ->
            receiptDetailRepository.save(
                ReceiptDetail(
                    receipt = receipt,
                    itemType = "MATERIAL",
                    itemName = m.material.materialName,
                    quantity = m.amount,
                    unitPrice = m.price,
                    totalPrice = m.amount * m.price
                )
            )
        }
        return receipt.toDTO()
    }

    override fun getListReceipts(
        search: String?,
        fromDate: LocalDate?,
        toDate: LocalDate?,
        paymentType: PaymentType?,
        page: Pageable
    ): Page<ReceiptDTO> {

        val receipts = receiptRepository.getListReceipt(search, fromDate, toDate, paymentType, page)

        return receipts.map { it.toDTO() }
    }

    override fun getReceiptDetail(receiptId: Long): ReceiptDTO {
        val receipt = receiptRepository.findById(receiptId)
            .orElseThrow{
                BusinessException(ErrorCode.NOT_FOUND, "Receipt not found!")
            }
        return receipt.toDTO()
    }


    private fun Receipt.toDTO() = ReceiptDTO (
        receiptId = this.receiptId,
        patientName = this.treatment.patient.patientName,
        patientPhone = this.treatment.patient.phone,
        treatmentId = this.treatment.treatmentId,
        treatmentName = this.patientRecord?.treatmentDescription
            ?: "",
        price = this.price,
        discount = this.discount,
        totalPrice = this.totalPrice,
        payment = this.payment,
        date = this.date,
        debit = this.debit,
        paymentType = this.paymentType,
    )
}