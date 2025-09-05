package org.vn.dentalClinicMgt.controller.auth

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.auth.ReqLoginDTO
import org.vn.dentalClinicMgt.domain.dto.auth.ResLoginDTO
import org.vn.dentalClinicMgt.service.UserService
import org.vn.dentalClinicMgt.utils.SecurityUtil

@Controller
class AuthMutation(
    private val userService: UserService,
    private val securityUtil: SecurityUtil,
){

    @MutationMapping
    fun login(@Argument input: ReqLoginDTO): ResLoginDTO {
        val user = userService.validateUserLogin(input.username, input.password)

        val userLogin = ResLoginDTO.UserLogin(
            userId = user.userId,
            username = user.username,
            fullName = user.fullName,
            email = user.email,
            role = user.role.roleName
        )

        val accessToken = securityUtil.createAccessToken(user.username, userLogin)
        val refreshToken = securityUtil.createRefreshToken(user.username, userLogin)

        println("db hash: ${user.password}")

        return ResLoginDTO(accessToken, refreshToken, userLogin)
    }
}