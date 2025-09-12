package org.vn.dentalClinicMgt.utils.exception

import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.execution.NonNullableValueCoercedAsNullException
import graphql.schema.DataFetchingEnvironment
import jakarta.validation.ConstraintViolationException
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException

@Component
class GraphQLExceptionHandler : DataFetcherExceptionResolverAdapter() {

    override fun resolveToSingleError(ex: Throwable, env: DataFetchingEnvironment): GraphQLError {
        return when (ex) {
            is BusinessException -> GraphqlErrorBuilder.newError(env)
                .message(ex.message)
                .extensions(
                    mapOf(
                        "http_code" to ex.errorCode.httpCode,
                        "http_message" to ex.errorCode.httpMessage,
                        "error_code" to ex.errorCode.name,
                        "sub_code" to ex.subCode
                    ).filterValues { it != null }
                )
                .build()

            is NotFoundException -> GraphqlErrorBuilder.newError(env)
                .message(ex.message ?: "Resource not found")
                .extensions(
                    mapOf(
                        "http_code" to ErrorCode.NOT_FOUND.httpCode,
                        "http_message" to ErrorCode.NOT_FOUND.httpMessage,
                        "error_code" to ErrorCode.NOT_FOUND.name
                    )
                )
                .build()

            // Thêm xử lý cho lỗi validation
            is ConstraintViolationException -> {
                val messages = ex.constraintViolations.map { it.message }.joinToString(", ")
                GraphqlErrorBuilder.newError(env)
                    .message(messages)
                    .extensions(
                        mapOf(
                            "http_code" to ErrorCode.BAD_REQUEST.httpCode,
                            "http_message" to ErrorCode.BAD_REQUEST.httpMessage,
                            "error_code" to ErrorCode.BAD_REQUEST.name
                        )
                    )
                    .build()
            }

            is MethodArgumentNotValidException -> {
                val messages = ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }
                    .joinToString(", ")
                GraphqlErrorBuilder.newError(env)
                    .message(messages)
                    .extensions(
                        mapOf(
                            "http_code" to ErrorCode.BAD_REQUEST.httpCode,
                            "http_message" to ErrorCode.BAD_REQUEST.httpMessage,
                            "error_code" to ErrorCode.BAD_REQUEST.name
                        )
                    )
                    .build()
            }

            // Input null cho tham số NonNull
            is NonNullableValueCoercedAsNullException -> {
                GraphqlErrorBuilder.newError(env)
                    .message("Input must not be null")
                    .extensions(
                        mapOf(
                            "http_code" to 400,
                            "http_message" to "BAD_REQUEST",
                            "error_code" to "NULL_INPUT"
                        )
                    )
                    .build()
            }

            is AuthorizationDeniedException -> {
                val auth = SecurityContextHolder.getContext().authentication
                if (auth == null || !auth.isAuthenticated) {
                    GraphqlErrorBuilder.newError(env)
                        .message("Bạn cần đăng nhập trước khi thực hiện hành động này!")
                        .extensions(
                            mapOf(
                                "http_code" to 401,
                                "http_message" to "UNAUTHORIZED",
                                "error_code" to "UNAUTHENTICATED"
                            )
                        )
                        .build()
                } else {
                    GraphqlErrorBuilder.newError(env)
                        .message("Bạn không có quyền thực hiện hành động này!")
                        .extensions(
                            mapOf(
                                "http_code" to 403,
                                "http_message" to "FORBIDDEN",
                                "error_code" to "ACCESS_DENIED"
                            )
                        )
                        .build()
                }
            }

            else -> {
                // In stacktrace ra console để debug
                println("Unhandled exception: ${ex::class.simpleName} - ${ex.message}")
                ex.printStackTrace()

                // Trả về message chi tiết trong DEV mode (hoặc ẩn trong PROD)
                GraphqlErrorBuilder.newError(env)
                    .message("Unexpected internal server error")
                    .extensions(
                        mapOf(
                            "http_code" to ErrorCode.INTERNAL_ERROR.httpCode,
                            "http_message" to ErrorCode.INTERNAL_ERROR.httpMessage,
                            "error_code" to ErrorCode.INTERNAL_ERROR.name,
                            "exception" to ex::class.simpleName  // cho biết loại exception
                        )
                    )
                    .build()
            }

        }
    }
}