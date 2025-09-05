package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "timekeeping")
data class Timekeeping(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val timekeepingId: Long = 0,

    @ManyToOne @JoinColumn(name = "userId")
    val user: User? = null,

    val timeCheck: LocalDateTime?,
    val timeCheckout: LocalDateTime?
)

