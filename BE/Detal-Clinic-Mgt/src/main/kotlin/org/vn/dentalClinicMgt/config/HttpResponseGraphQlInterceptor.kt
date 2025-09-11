package org.vn.dentalClinicMgt.config

import jakarta.servlet.http.HttpServletResponse
import org.springframework.graphql.server.WebGraphQlInterceptor
import org.springframework.graphql.server.WebGraphQlRequest
import org.springframework.graphql.server.WebGraphQlResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class HttpServletGraphQLInterceptor : WebGraphQlInterceptor {
    override fun intercept(
        request: WebGraphQlRequest,
        chain: WebGraphQlInterceptor.Chain
    ): Mono<WebGraphQlResponse> {

        request.configureExecutionInput { _, builder ->
            builder.graphQLContext { ctx ->
                // lấy HttpServletRequest từ attributes
                val servletRequest = request.attributes["httpRequest"] as? jakarta.servlet.http.HttpServletRequest
                if (servletRequest != null) {
                    ctx.put(jakarta.servlet.http.HttpServletRequest::class.java, servletRequest)

                    servletRequest.cookies?.forEach {
                        println("Cookie nhận được: ${it.name}=${it.value}")
                    }
                }

                // lấy HttpServletResponse từ attributes
                val servletResponse = request.attributes["httpResponse"] as? jakarta.servlet.http.HttpServletResponse
                if (servletResponse != null) {
                    ctx.put(jakarta.servlet.http.HttpServletResponse::class.java, servletResponse)
                }
            }.build()
        }

        return chain.next(request)
    }
}




