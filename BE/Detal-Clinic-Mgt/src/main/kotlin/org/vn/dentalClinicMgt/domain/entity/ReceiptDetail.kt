package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "receiptdetail")
data class ReceiptDetail(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val receiptDetailId: Long = 0,

    @ManyToOne @JoinColumn(name = "receiptId")
    val receipt: Receipt,

    var itemType: String,   // "SERVICE" hoặc "MATERIAL"
    var itemName: String,   // tên service hoặc material
    var quantity: Int = 1,  // với material thì >1, còn service thì =1
    var unitPrice: Int,     // giá 1 đơn vị
    var totalPrice: Int     // quantity * unitPrice
)
