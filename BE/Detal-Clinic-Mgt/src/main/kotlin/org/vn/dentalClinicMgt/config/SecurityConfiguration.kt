package org.vn.dentalClinicMgt.config

import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.util.Base64
import org.vn.dentalClinicMgt.utils.SecurityUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import org.springframework.security.config.Customizer

@Configuration
@EnableMethodSecurity(securedEnabled = true)
class SecurityConfiguration(
    @Value("\${application.security.jwt.base64-secret}") private val jwtKey: String
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val whiteList = arrayOf(
            "/graphql", "/graphiql", "/api/v1/auth/login"
        )

        http.csrf { it.disable() }
            .authorizeHttpRequests { authz ->
                authz.requestMatchers(*whiteList).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        return http.build()
    }

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val converter = JwtGrantedAuthoritiesConverter().apply {
            setAuthorityPrefix("")
            setAuthoritiesClaimName("permissions")
        }
        return JwtAuthenticationConverter().apply {
            setJwtGrantedAuthoritiesConverter(converter)
        }
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val secretKey = getSecretKey()
        return NimbusJwtDecoder.withSecretKey(secretKey)
            .macAlgorithm(SecurityUtil.JWT_ALGORITHM)
            .build()
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        return NimbusJwtEncoder(ImmutableSecret(getSecretKey()))
    }

    private fun getSecretKey(): SecretKey {
        val keyBytes = Base64.from(jwtKey).decode()
        return SecretKeySpec(keyBytes, 0, keyBytes.size, SecurityUtil.JWT_ALGORITHM.name)
    }

}

