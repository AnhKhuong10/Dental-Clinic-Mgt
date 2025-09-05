package org.vn.dentalClinicMgt.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "permission")
data class Permission(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val permissionId: Long = 0,

    @Column(nullable = false)
    val permissionName: String,

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val rolePermissions: List<RolePermissionMap> = mutableListOf()
)
