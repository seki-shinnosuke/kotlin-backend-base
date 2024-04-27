package seki.kotlin.backend.base.batch.core.service

/**
 * バッチ処理インターフェース
 * 前処理、メイン処理、後処理を定義
 */
interface BatchService {
    fun processBefore()

    fun process()

    fun processAfter()
}
