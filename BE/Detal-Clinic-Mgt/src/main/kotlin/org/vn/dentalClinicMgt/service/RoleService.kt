package org.vn.dentalClinicMgt.service

import org.vn.dentalClinicMgt.domain.entity.Role

interface RoleService {
    fun findAll(): List<Role>
}