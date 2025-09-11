package org.vn.dentalClinicMgt.domain.entity

import com.fasterxml.jackson.annotation.JsonFormat
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

    var fullName: String?,
    @Column(nullable = false)
    var password: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var birthdate: LocalDate,
    var phone: String?,
    val email: String?,
    var salary: Int?,
    var enabled: Boolean = true,
    var refreshToken: String? = null,

    @ManyToOne @JoinColumn(name = "roleId")
    var role: Role
)