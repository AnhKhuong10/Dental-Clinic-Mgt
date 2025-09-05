package org.vn.dentalClinicMgt.utils.exception

import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import jakarta.validation.ConstraintViolationException
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
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