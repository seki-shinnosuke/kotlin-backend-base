/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.mapper

import java.sql.JDBCType
import java.time.LocalDateTime
import org.mybatis.dynamic.sql.AliasableSqlTable
import org.mybatis.dynamic.sql.util.kotlin.elements.column

object AccessLogManagementDynamicSqlSupport {
    val accessLogManagement = AccessLogManagement()

    val accessLogId = accessLogManagement.accessLogId

    val ipAddress = accessLogManagement.ipAddress

    val accessType = accessLogManagement.accessType

    val accessDatetime = accessLogManagement.accessDatetime

    val userAgent = accessLogManagement.userAgent

    val accessLimitDatetime = accessLogManagement.accessLimitDatetime

    val createBy = accessLogManagement.createBy

    val createAt = accessLogManagement.createAt

    val updateBy = accessLogManagement.updateBy

    val updateAt = accessLogManagement.updateAt

    class AccessLogManagement : AliasableSqlTable<AccessLogManagement>("access_log_management", ::AccessLogManagement) {
        val accessLogId = column<Long>(name = "access_log_id", jdbcType = JDBCType.BIGINT)

        val ipAddress = column<String>(name = "ip_address", jdbcType = JDBCType.VARCHAR)

        val accessType = column<String>(name = "access_type", jdbcType = JDBCType.VARCHAR)

        val accessDatetime = column<LocalDateTime>(name = "access_datetime", jdbcType = JDBCType.TIMESTAMP)

        val userAgent = column<String>(name = "user_agent", jdbcType = JDBCType.VARCHAR)

        val accessLimitDatetime = column<LocalDateTime>(name = "access_limit_datetime", jdbcType = JDBCType.TIMESTAMP)

        val createBy = column<String>(name = "create_by", jdbcType = JDBCType.VARCHAR)

        val createAt = column<LocalDateTime>(name = "create_at", jdbcType = JDBCType.TIMESTAMP)

        val updateBy = column<String>(name = "update_by", jdbcType = JDBCType.VARCHAR)

        val updateAt = column<LocalDateTime>(name = "update_at", jdbcType = JDBCType.TIMESTAMP)
    }
}