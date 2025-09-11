package org.vn.dentalClinicMgt.controller.user

import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.user.CreateUserInput
import org.vn.dentalClinicMgt.domain.dto.user.UpdateUserInput
import org.vn.dentalClinicMgt.domain.dto.user.UserDTO
import org.vn.dentalClinicMgt.domain.dto.user.UserPage
import org.vn.dentalClinicMgt.domain.entity.User
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

    @PreAuthorize("hasAuthority('USER_VIEW_DETAIL')")
    @QueryMapping
    fun userByUserId(@Argument userId: Long): UserDTO? {

        return userService.findUserByUserId(userId)
    }
}

@Controller
class UserMutation(
    private val userService: UserService,
){
    @PreAuthorize("hasAuthority('USER_CREATE')")
    @MutationMapping
    fun createUser(@Argument @Valid input: CreateUserInput): UserDTO {
        println("Create user = $input\n")
        return userService.createUser(input)
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @MutationMapping
    fun updateUser(@Argument @Valid input: UpdateUserInput): UserDTO {
        println("Update user = $input\n")
        return userService.updateUser(input)
    }
}

