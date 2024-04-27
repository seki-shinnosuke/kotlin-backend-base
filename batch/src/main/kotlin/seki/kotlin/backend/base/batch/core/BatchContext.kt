package seki.kotlin.backend.base.batch.core

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import seki.kotlin.backend.base.common.component.VirtualDatetimeComponent
import seki.kotlin.backend.base.common.db.mapper.BatchStartControlMapper

/**
 * バッチコンテキスト
 * バッチ処理は起動 > 対象データ抽出 > 1レコードずつ処理 > 終了というトランザクションで動くため
 * トランザクションを跨いだデータ保持をするために本クラスを利用する
 */
@Component
class BatchContext(
    @Value("\${BATCH_ID}")
    val batchId: String,
    val virtualDatetimeComponent: VirtualDatetimeComponent,
    val batchStartControlMapper: BatchStartControlMapper,
) {
    /** バッチ起動引数 */
    var args: List<String?> = emptyList()

    /** バッチ名 */
    var batchName: String = ""

    /** 処理成功数 */
    var successCount: Long = 0

    /** 処理失敗数 */
    var failCount: Long = 0

    /** 処理スキップ数 */
    var skipCount: Long = 0

    /** 例外 */
    var exception: Throwable? = null

    /** 例外の状態を返却 */
    fun hasError() = exception != null

    fun validate() {
        exception =
            when {
                batchId.isEmpty() -> RuntimeException("Not been set Batch ID.")
                else -> null
            }
    }

    fun successCountUp() {
        successCount++
    }

    fun failCountUp() {
        failCount++
    }

    fun skipCountUp() {
        skipCount++
    }

    fun totalCount(): Long {
        return successCount + failCount + skipCount
    }

    fun cleanUp() {
        successCount = 0
        failCount = 0
        skipCount = 0
        exception = null
    }
}
