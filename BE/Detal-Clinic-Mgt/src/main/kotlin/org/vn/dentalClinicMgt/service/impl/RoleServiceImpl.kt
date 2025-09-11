package org.vn.dentalClinicMgt.service.impl

import org.springframework.stereotype.Service
import org.vn.dentalClinicMgt.domain.entity.Role
import org.vn.dentalClinicMgt.repository.RoleRepository
import org.vn.dentalClinicMgt.service.RoleService

@Service
class RoleServiceImpl(
    private val roleRepository: RoleRepository
) : RoleService {
    override fun findAll(): List<Role> {
        return roleRepository.findAll()
    }
}