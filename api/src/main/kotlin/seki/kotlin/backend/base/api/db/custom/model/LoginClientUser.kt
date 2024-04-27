package seki.kotlin.backend.base.api.db.custom.model

import java.time.LocalDateTime

data class LoginClientUser(
    val clientUserId: Long,
    val clientUserLoginId: String,
    val userName: String,
    val emailAddress: String?,
    val authority: String,
    val password: String?,
    val passwordStatus: String?,
    val temporaryPassword: String?,
    val temporaryPasswordExpiryDatetime: LocalDateTime?,
)
