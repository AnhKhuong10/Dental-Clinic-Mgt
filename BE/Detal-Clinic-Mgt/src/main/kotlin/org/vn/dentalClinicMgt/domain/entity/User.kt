package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    val userId: Long = 0,

    @Column(nullable = false, unique = true)
    val username: String,

    val fullName: String?,
    @Column(nullable = false)
    val password: String,
    val birthdate: LocalDate,
    val phone: String?,
    val email: String?,
    val salary: Int?,
    val enabled: Boolean = true,

    @ManyToOne @JoinColumn(name = "roleId")
    val role: Role
)