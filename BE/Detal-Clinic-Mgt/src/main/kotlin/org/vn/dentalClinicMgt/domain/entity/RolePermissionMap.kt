package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "rolepermissionmap")
data class RolePermissionMap(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val rolePermissionMapId: Long = 0,

    @ManyToOne @JoinColumn(name = "roleId")
    val role: Role? = null,

    @ManyToOne @JoinColumn(name = "permissionId")
    val permission: Permission? = null
)