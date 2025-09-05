package org.vn.dentalClinicMgt.controller.user

import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.user.UserPage
import org.vn.dentalClinicMgt.service.UserService

@Controller
class UserQuery(
    private val userService: UserService,

){
    @PreAuthorize("hasAuthority('USER_VIEW')")
    @QueryMapping
    fun users(
        @Argument page: Int,
        @Argument size: Int,
        @Argument search: String?,
    ): UserPage {
        val pageable = PageRequest.of(page, size)
        val result = userService.listUsers(search, pageable)

        return UserPage(
            content = result.content,
            totalPages = result.totalPages,
            totalElements = result.totalElements,
            pageNumber = result.number,
            pageSize = result.size
        )
     }
}

