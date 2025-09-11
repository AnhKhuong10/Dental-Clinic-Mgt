package org.vn.dentalClinicMgt.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowedOrigins = listOf(
            "http://localhost:5173",      // FE
            "http://localhost:8080",      // BE
        )

        // Các method được phép
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")

        // Các header được phép
        config.allowedHeaders = listOf("Authorization", "Content-Type", "Accept")

        // Cho phép gửi cookie (bắt buộc để gửi refreshToken qua cookie)
        config.allowCredentials = true

        // Thời gian cache pre-flight (OPTIONS) = 1h
        config.maxAge = 3600

        config.exposedHeaders = listOf("Set-Cookie")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)

        return CorsFilter(source)
    }
}