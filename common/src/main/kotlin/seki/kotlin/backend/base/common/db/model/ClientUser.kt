/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.model

import java.time.LocalDateTime

data class ClientUser(
    var clientUserId: Long? = null,
    var clientUserLoginId: String? = null,
    var userName: String? = null,
    var emailAddress: String? = null,
    var authority: String? = null,
    var userStatus: String? = null,
    var lastLoginDatetime: LocalDateTime? = null,
    var registrationUserId: Long? = null,
    var registrationDatetime: LocalDateTime? = null,
    var updateUserId: Long? = null,
    var updateDatetime: LocalDateTime? = null,
    var createBy: String? = null,
    var createAt: LocalDateTime? = null,
    var updateBy: String? = null,
    var updateAt: LocalDateTime? = null
)