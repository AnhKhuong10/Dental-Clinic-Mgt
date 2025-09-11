package org.vn.dentalClinicMgt.utils

import org.vn.dentalClinicMgt.domain.dto.auth.ResLoginDTO
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.jwt.Jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Component
import org.vn.dentalClinicMgt.domain.entity.User
import org.vn.dentalClinicMgt.service.UserService
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import com.nimbusds.jose.util.Base64

@Component
class SecurityUtil(
    private val jwtEncoder: JwtEncoder,
    private val userService: UserService
) {
    companion object {
        val JWT_ALGORITHM: MacAlgorithm = MacAlgorithm.HS512

        fun getCurrentUserLogin(): Optional<String> {
            val authentication: Authentication? = SecurityContextHolder.getContext().authentication
            return Optional.ofNullable(extractPrincipal(authentication))
        }

        private fun extractPrincipal(authentication: Authentication?): String? {
            return when {
                authentication == null -> null
                authentication.principal is UserDetails -> (authentication.principal as UserDetails).username
                authentication.principal is Jwt -> (authentication.principal as Jwt).subject
                authentication.principal is String -> authentication.principal as String
                else -> null
            }
        }

    }
    @Value("\${application.security.jwt.access-token-validity-in-seconds}")
    private var accessTokenExpiration: Long = 0

    @Value("\${application.security.jwt.refresh-token-validity-in-seconds}")
    private var refreshTokenExpiration: Long = 0

    @Value("\${application.security.jwt.base64-secret}")
    private var jwtKey: String? = null

    fun createAccessToken(username: String, dto: ResLoginDTO.UserLogin): String {
        val now = Instant.now()
        val validity = now.plus(accessTokenExpiration, ChronoUnit.SECONDS)
        val user: User = userService.handleGetUserByUsername(username)
        val role = user.role

        val permissions = role.rolePermissions
            .mapNotNull { it.permission?.permissionName }
            .toSet()

        val claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(username)
            .claim("user", dto)
            .claim("role", user.role.roleName)
            .claim("permissions", permissions)
            .build()

        val jwsHeader = JwsHeader.with(JWT_ALGORITHM).build()
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    fun createRefreshToken(username: String, dto: ResLoginDTO.UserLogin): String {
        val now = Instant.now()
        val validity = now.plus(refreshTokenExpiration, ChronoUnit.SECONDS)
        val user: User = userService.handleGetUserByUsername(username)
        val role = user.role

        val permissions = role.rolePermissions
            .mapNotNull { it.permission?.permissionName }
            .toSet()
        val claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(username)
            .claim("user", dto)
            .claim("role", user.role.roleName)
            .claim("permissions", permissions)
            .build()

        val jwsHeader = JwsHeader.with(JWT_ALGORITHM).build()
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    private fun getSecretKey(): SecretKey {
        val keyBytes = Base64.from(jwtKey).decode()
        return SecretKeySpec(keyBytes, 0, keyBytes.size, JWT_ALGORITHM.name)
    }


    fun checkValidRefreshToken(token: String): Jwt {
        //khởi tạo một bộ giải mã JWT
        val jwtDecoder = NimbusJwtDecoder.withSecretKey(getSecretKey())
            .macAlgorithm(SecurityUtil.JWT_ALGORITHM)
            .build()
        return try {
            jwtDecoder.decode(token)
        } catch (e: Exception) {
            println(">>> Refresh Token error: ${e.message}")
            throw e
        }
    }

}