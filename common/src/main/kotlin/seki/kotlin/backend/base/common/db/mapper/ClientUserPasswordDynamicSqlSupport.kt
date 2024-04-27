/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.mapper

import java.sql.JDBCType
import java.time.LocalDateTime
import org.mybatis.dynamic.sql.AliasableSqlTable
import org.mybatis.dynamic.sql.util.kotlin.elements.column

object ClientUserPasswordDynamicSqlSupport {
    val clientUserPassword = ClientUserPassword()

    val clientUserId = clientUserPassword.clientUserId

    val password = clientUserPassword.password

    val passwordStatus = clientUserPassword.passwordStatus

    val temporaryPassword = clientUserPassword.temporaryPassword

    val temporaryPasswordExpiryDatetime = clientUserPassword.temporaryPasswordExpiryDatetime

    val createBy = clientUserPassword.createBy

    val createAt = clientUserPassword.createAt

    val updateBy = clientUserPassword.updateBy

    val updateAt = clientUserPassword.updateAt

    class ClientUserPassword : AliasableSqlTable<ClientUserPassword>("client_user_password", ::ClientUserPassword) {
        val clientUserId = column<Long>(name = "client_user_id", jdbcType = JDBCType.BIGINT)

        val password = column<String>(name = "password", jdbcType = JDBCType.VARCHAR)

        val passwordStatus = column<String>(name = "password_status", jdbcType = JDBCType.CHAR)

        val temporaryPassword = column<String>(name = "temporary_password", jdbcType = JDBCType.VARCHAR)

        val temporaryPasswordExpiryDatetime = column<LocalDateTime>(name = "temporary_password_expiry_datetime", jdbcType = JDBCType.TIMESTAMP)

        val createBy = column<String>(name = "create_by", jdbcType = JDBCType.VARCHAR)

        val createAt = column<LocalDateTime>(name = "create_at", jdbcType = JDBCType.TIMESTAMP)

        val updateBy = column<String>(name = "update_by", jdbcType = JDBCType.VARCHAR)

        val updateAt = column<LocalDateTime>(name = "update_at", jdbcType = JDBCType.TIMESTAMP)
    }
}