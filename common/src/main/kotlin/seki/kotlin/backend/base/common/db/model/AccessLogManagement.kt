/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.model

import java.time.LocalDateTime

data class AccessLogManagement(
    var accessLogId: Long? = null,
    var ipAddress: String? = null,
    var accessType: String? = null,
    var accessDatetime: LocalDateTime? = null,
    var userAgent: String? = null,
    var accessLimitDatetime: LocalDateTime? = null,
    var createBy: String? = null,
    var createAt: LocalDateTime? = null,
    var updateBy: String? = null,
    var updateAt: LocalDateTime? = null
)