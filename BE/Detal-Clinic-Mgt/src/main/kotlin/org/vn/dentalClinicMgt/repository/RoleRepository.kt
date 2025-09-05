package org.vn.dentalClinicMgt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.vn.dentalClinicMgt.domain.entity.Role

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
}