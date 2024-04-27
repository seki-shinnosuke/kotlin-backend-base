package seki.kotlin.backend.base.batch.core.exception

/**
 * レコード単位処理時に、処理レコードをスキップさせる場合に利用する例外
 */
class RecordSkipException(
    override val message: String?,
) : RuntimeException()
