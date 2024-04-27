package seki.kotlin.backend.base.batch.core.exception

/**
 * リトライさせる場合に利用する例外
 */
class RetryException(
    override val message: String?,
) : RuntimeException()
