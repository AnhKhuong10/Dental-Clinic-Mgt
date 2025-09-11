package org.vn.dentalClinicMgt.config

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class GraphQLServletFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (request is HttpServletRequest && response is HttpServletResponse) {
            request.setAttribute("httpRequest", request)
            request.setAttribute("httpResponse", response)
        }
        chain.doFilter(request, response)
    }
}