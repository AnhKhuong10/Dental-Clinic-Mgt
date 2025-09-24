package org.vn.dentalClinicMgt.utils.constants

enum class RecordStatus {
    NOT_STARTED,   // chưa khám
    IN_PROGRESS,   // đang khám, có service đang thực hiện
    COMPLETED,     // buổi khám đã xong
    PAID           // buổi khám này không còn nợ
}