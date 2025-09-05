package org.vn.dentalClinicMgt.utils.exception

enum class ErrorCode(val httpCode: Int, val httpMessage: String) {
    BAD_REQUEST(400, "BAD_REQUEST"),
    NOT_FOUND(404, "NOT_FOUND"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    INTERNAL_ERROR(500, "INTERNAL_SERVER_ERROR")
}
