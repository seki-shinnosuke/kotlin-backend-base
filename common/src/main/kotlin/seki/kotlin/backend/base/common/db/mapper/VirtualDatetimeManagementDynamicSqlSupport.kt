/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.mapper

import java.sql.JDBCType
import java.time.LocalDateTime
import org.mybatis.dynamic.sql.AliasableSqlTable
import org.mybatis.dynamic.sql.util.kotlin.elements.column

object VirtualDatetimeManagementDynamicSqlSupport {
    val virtualDatetimeManagement = VirtualDatetimeManagement()

    val virtualDatetime = virtualDatetimeManagement.virtualDatetime

    val registrationDatetime = virtualDatetimeManagement.registrationDatetime

    class VirtualDatetimeManagement : AliasableSqlTable<VirtualDatetimeManagement>("virtual_datetime_management", ::VirtualDatetimeManagement) {
        val virtualDatetime = column<LocalDateTime>(name = "virtual_datetime", jdbcType = JDBCType.TIMESTAMP)

        val registrationDatetime = column<LocalDateTime>(name = "registration_datetime", jdbcType = JDBCType.TIMESTAMP)
    }
}