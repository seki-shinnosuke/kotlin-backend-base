package seki.kotlin.backend.base.api.component

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementDynamicSqlSupport
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementMapper
import seki.kotlin.backend.base.common.db.mapper.delete
import seki.kotlin.backend.base.common.db.mapper.insertSelective
import seki.kotlin.backend.base.common.db.mapper.select
import seki.kotlin.backend.base.common.db.mapper.selectOne
import seki.kotlin.backend.base.common.db.mapper.updateByPrimaryKeySelective
import seki.kotlin.backend.base.common.db.model.AccessLogManagement
import seki.kotlin.backend.base.common.enumeration.AccessType
import seki.kotlin.backend.base.common.extension.isBeforeEqual
import java.time.LocalDateTime

/**
 * アクセスログ管理コンポーネント
 * 総当たり攻撃対策として指定の時間内に指定の回数アクセスが行われた場合は指定時間までアクセスを拒否する
 */
@Component
class AccessLogManagementComponent(
    private val accessLogManagementMapper: AccessLogManagementMapper,
) {
    companion object {
        // アクセス制限件数
        private const val ACCESS_LIMIT_SIZE = 5

        // アクセス制限チェック範囲時間(分)
        private const val ACCESS_LIMIT_RANGE_MINUTES = 10L

        // アクセス制限時間（分）
        private const val ACCESS_LIMIT_MINUTES = 60L
    }

    /**
     * アクセス可能かを判定する
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Throwable::class])
    fun canAccess(
        ipAddress: String,
        userAgent: String,
        systemDatetime: LocalDateTime,
        accessType: AccessType,
        requestURI: String,
    ): Pair<Boolean, LocalDateTime?> {
        val accessLog =
            accessLogManagementMapper.selectOne {
                where {
                    AccessLogManagementDynamicSqlSupport.ipAddress.isEqualTo(ipAddress)
                    and {
                        AccessLogManagementDynamicSqlSupport.accessType.isEqualTo(accessType.name)
                    }
                }
                orderBy(AccessLogManagementDynamicSqlSupport.accessDatetime.descending())
                limit(1)
            }

        if (accessLog?.accessLimitDatetime != null && systemDatetime.isBeforeEqual(accessLog.accessLimitDatetime)) {
            return Pair(false, accessLog.accessLimitDatetime)
        }
        // アクセスログ管理登録
        accessLogManagementMapper.insertSelective(
            AccessLogManagement(
                ipAddress = ipAddress,
                accessType = accessType.name,
                accessDatetime = systemDatetime,
                userAgent = userAgent,
                accessLimitDatetime = null,
                createBy = requestURI,
                createAt = LocalDateTime.now(),
                updateBy = requestURI,
                updateAt = LocalDateTime.now(),
            ),
        )
        return Pair(true, null)
    }

    /**
     * アクセス制限
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Throwable::class])
    fun accessLimit(
        ipAddress: String,
        systemDatetime: LocalDateTime,
        accessType: AccessType,
        requestURI: String,
    ): Int {
        // 直近のアクセスログを取得
        val accessLogs =
            accessLogManagementMapper.select {
                where {
                    AccessLogManagementDynamicSqlSupport.ipAddress.isEqualTo(ipAddress)
                    and {
                        AccessLogManagementDynamicSqlSupport.accessType.isEqualTo(accessType.name)
                    }
                    and {
                        AccessLogManagementDynamicSqlSupport.accessDatetime.isGreaterThanOrEqualTo(
                            systemDatetime.minusMinutes(ACCESS_LIMIT_RANGE_MINUTES),
                        )
                    }
                }
                orderBy(AccessLogManagementDynamicSqlSupport.accessDatetime.descending())
            }
        // アクセス回数が上限に達していた場合は制限日時を設定
        if (accessLogs.size >= ACCESS_LIMIT_SIZE) {
            accessLogManagementMapper.updateByPrimaryKeySelective(
                AccessLogManagement(
                    accessLogId = accessLogs.first().accessLogId,
                    accessLimitDatetime = systemDatetime.plusMinutes(ACCESS_LIMIT_MINUTES),
                    accessType = accessType.name,
                    updateBy = requestURI,
                    updateAt = LocalDateTime.now(),
                ),
            )
        }
        // 残アクセス可能数
        return ACCESS_LIMIT_SIZE - accessLogs.size
    }

    /**
     * アクセス制限解除
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Throwable::class])
    fun accessLimitRelease(
        ipAddress: String,
        accessType: AccessType,
    ) {
        accessLogManagementMapper.delete {
            where {
                AccessLogManagementDynamicSqlSupport.ipAddress.isEqualTo(ipAddress)
                and {
                    AccessLogManagementDynamicSqlSupport.accessType.isEqualTo(accessType.name)
                }
            }
        }
    }
}
