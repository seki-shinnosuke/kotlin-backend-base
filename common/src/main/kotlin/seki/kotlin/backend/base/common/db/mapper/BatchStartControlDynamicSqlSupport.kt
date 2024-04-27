/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.mapper

import java.sql.JDBCType
import java.time.LocalDateTime
import org.mybatis.dynamic.sql.AliasableSqlTable
import org.mybatis.dynamic.sql.util.kotlin.elements.column

object BatchStartControlDynamicSqlSupport {
    val batchStartControl = BatchStartControl()

    val batchId = batchStartControl.batchId

    val startDatetime = batchStartControl.startDatetime

    val createBy = batchStartControl.createBy

    val createAt = batchStartControl.createAt

    val updateBy = batchStartControl.updateBy

    val updateAt = batchStartControl.updateAt

    class BatchStartControl : AliasableSqlTable<BatchStartControl>("batch_start_control", ::BatchStartControl) {
        val batchId = column<String>(name = "batch_id", jdbcType = JDBCType.VARCHAR)

        val startDatetime = column<LocalDateTime>(name = "start_datetime", jdbcType = JDBCType.TIMESTAMP)

        val createBy = column<String>(name = "create_by", jdbcType = JDBCType.VARCHAR)

        val createAt = column<LocalDateTime>(name = "create_at", jdbcType = JDBCType.TIMESTAMP)

        val updateBy = column<String>(name = "update_by", jdbcType = JDBCType.VARCHAR)

        val updateAt = column<LocalDateTime>(name = "update_at", jdbcType = JDBCType.TIMESTAMP)
    }
}