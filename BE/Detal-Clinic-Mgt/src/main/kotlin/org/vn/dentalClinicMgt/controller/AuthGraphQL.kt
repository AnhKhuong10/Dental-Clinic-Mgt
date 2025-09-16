package org.vn.dentalClinicMgt.controller

import graphql.schema.DataFetchingEnvironment
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Controller
import org.vn.dentalClinicMgt.domain.dto.auth.ReqLoginDTO
import org.vn.dentalClinicMgt.domain.dto.auth.ResLoginDTO
import org.vn.dentalClinicMgt.service.UserService
import org.vn.dentalClinicMgt.utils.SecurityUtil
import org.springframework.http.HttpHeaders

@Controller
class AuthMutation(
    private val userService: UserService,
    private val securityUtil: SecurityUtil,
){

    @MutationMapping
    fun login(@Argument input: ReqLoginDTO, env: DataFetchingEnvironment): ResLoginDTO {
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

        userService.updateUserToken(user.userId, refreshToken)

        val response = env.getGraphQlContext().get<HttpServletResponse>(HttpServletResponse::class.java)
        println("response: $response")
        if (response != null) {
            val cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false) // true nếu HTTPS
                .path("/")
                .maxAge(60 * 60 * 24 * 30)
                .sameSite("Lax")
                .build()

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
        }

        return ResLoginDTO(accessToken, userLogin)
    }

    @MutationMapping
    fun getRefreshToken(env: DataFetchingEnvironment): ResLoginDTO {
        val request = env.getGraphQlContext().get<HttpServletRequest>(HttpServletRequest::class.java)
        val response = env.getGraphQlContext().get<HttpServletResponse>(HttpServletResponse::class.java)

        // Lấy refreshToken từ cookie
        val cookies = request?.cookies
        println("cookie: $cookies")
        println("request: $request")
        println("response: $response")
        val cookieHeader = request?.getHeader("Cookie")
        println("Cookie header: $cookieHeader")
        val refreshToken = cookies?.firstOrNull { it.name == "refresh_token" }?.value
            ?: throw RuntimeException("Bạn không có refresh token ở cookie")

        println("\nrefreshToken: $refreshToken")

        // Validate refreshToken
        val decodedToken = securityUtil.checkValidRefreshToken(refreshToken)
        val username = decodedToken.subject
        println("username: $username")
        val user = userService.getUserByUsernameAndRefreshToken(refreshToken, username)
        println("user: $user")
        println("DB refreshToken length = ${user?.refreshToken?.length}")
        println("Param refreshToken length = ${refreshToken.length}")
        val currentUser = userService.getUserByUsernameAndRefreshToken(refreshToken, username)
            ?: throw RuntimeException("Refresh Token không hợp lệ")
        println("currentUser: $currentUser")

        // build user dto
        val userLogin = ResLoginDTO.UserLogin(
            userId = currentUser.userId,
            username = currentUser.username,
            fullName = currentUser.fullName,
            email = currentUser.email,
            role = currentUser.role.roleName
        )

        // tạo accessToken mới
        val newAccessToken = securityUtil.createAccessToken(username, userLogin)

        return ResLoginDTO(newAccessToken, userLogin)
    }


}