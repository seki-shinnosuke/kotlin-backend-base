/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.model

import java.time.LocalDateTime

data class ClientUserPassword(
    var clientUserId: Long? = null,
    var password: String? = null,
    var passwordStatus: String? = null,
    var temporaryPassword: String? = null,
    var temporaryPasswordExpiryDatetime: LocalDateTime? = null,
    var createBy: String? = null,
    var createAt: LocalDateTime? = null,
    var updateBy: String? = null,
    var updateAt: LocalDateTime? = null
)