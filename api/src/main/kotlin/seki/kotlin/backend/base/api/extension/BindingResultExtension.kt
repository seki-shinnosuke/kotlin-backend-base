package seki.kotlin.backend.base.api.extension

import org.springframework.validation.BindingResult

/**
 * バリデーションエラー内容を結合し文字列で返却する
 */
fun BindingResult.fieldErrorsJoinToString(): String = this.fieldErrors.joinToString { "${it.field}：${it.defaultMessage}" }
