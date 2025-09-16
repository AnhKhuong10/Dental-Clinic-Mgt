package org.vn.dentalClinicMgt.controller

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.entity.Role
import org.vn.dentalClinicMgt.service.RoleService

@Controller
class RoleGraphQL(
    private val roleService: RoleService,
) {
   @QueryMapping
   fun listRoles(): List<Role> {
       return roleService.findAll()
   }
}