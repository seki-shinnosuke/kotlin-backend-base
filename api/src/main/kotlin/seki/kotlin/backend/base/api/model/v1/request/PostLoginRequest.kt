package seki.kotlin.backend.base.api.model.v1.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class PostLoginRequest(
    /** クライアントユーザーログインID */
    @field:[
    NotEmpty
    Size(max = 50)
    ] val clientUserLoginId: String?,
    /** パスワード */
    @field:[
    NotEmpty
    Size(max = 32)
    ] val password: String?,
)
