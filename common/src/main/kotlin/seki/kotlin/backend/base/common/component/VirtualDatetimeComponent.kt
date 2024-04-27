package seki.kotlin.backend.base.common.component

import org.springframework.stereotype.Component
import seki.kotlin.backend.base.common.config.property.VirtualDatetimeProperties
import seki.kotlin.backend.base.common.db.mapper.VirtualDatetimeManagementMapper
import seki.kotlin.backend.base.common.db.mapper.select
import seki.kotlin.backend.base.common.extension.toFormatString
import seki.kotlin.backend.base.common.log.MsgLogger
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

/**
 * 仮想日時コンポーネント
 */
@Component
class VirtualDatetimeComponent(
    private val msgLogger: MsgLogger,
    private val virtualDatetimeManagementMapper: VirtualDatetimeManagementMapper,
    private val virtualDatetimeProperties: VirtualDatetimeProperties,
) {
    companion object {
        const val TIME_ZONE = "Asia/Tokyo"
    }

    /**
     * 仮想日時を返却
     * 仮想日時TBLに登録されている仮想日時に対し登録日時から実際に過ぎた時間を加算して返却する
     */
    fun now(): LocalDateTime {
        // システム日時取得
        val systemDatetime = LocalDateTime.now(ZoneId.of(TIME_ZONE))

        // 仮想日時を利用しない場合(商用環境など)はシステム日時を返却
        if (!virtualDatetimeProperties.isVirtualDatetimeOn) {
            return systemDatetime
        }

        // 仮想日時取得
        val virtualDatetimeList = virtualDatetimeManagementMapper.select { allRows() }
        // 仮想日時未設定の場合はシステム日時を返却
        if (virtualDatetimeList.isEmpty()) {
            return systemDatetime
        }

        // 仮想日時が設定されている場合は先頭レコードを取得
        val virtualDateFirst = virtualDatetimeList.first()
        // 仮想日時 + (システム日時 - 登録日時)を計算して返却する
        val virtualDatetime =
            virtualDateFirst.virtualDatetime?.plus(
                ChronoUnit.MILLIS.between(
                    virtualDateFirst.registrationDatetime,
                    systemDatetime,
                ),
                ChronoUnit.MILLIS,
            ) ?: throw Throwable("Failed get virtual datetime.")
        msgLogger.info("VirtualDatetime：{}", virtualDatetime.toFormatString())
        return virtualDatetime
    }
}
