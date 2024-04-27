/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.mapper

import java.sql.JDBCType
import java.time.LocalDateTime
import org.mybatis.dynamic.sql.AliasableSqlTable
import org.mybatis.dynamic.sql.util.kotlin.elements.column

object ClientUserDynamicSqlSupport {
    val clientUser = ClientUser()

    val clientUserId = clientUser.clientUserId

    val clientUserLoginId = clientUser.clientUserLoginId

    val userName = clientUser.userName

    val emailAddress = clientUser.emailAddress

    val authority = clientUser.authority

    val userStatus = clientUser.userStatus

    val lastLoginDatetime = clientUser.lastLoginDatetime

    val registrationUserId = clientUser.registrationUserId

    val registrationDatetime = clientUser.registrationDatetime

    val updateUserId = clientUser.updateUserId

    val updateDatetime = clientUser.updateDatetime

    val createBy = clientUser.createBy

    val createAt = clientUser.createAt

    val updateBy = clientUser.updateBy

    val updateAt = clientUser.updateAt

    class ClientUser : AliasableSqlTable<ClientUser>("client_user", ::ClientUser) {
        val clientUserId = column<Long>(name = "client_user_id", jdbcType = JDBCType.BIGINT)

        val clientUserLoginId = column<String>(name = "client_user_login_id", jdbcType = JDBCType.VARCHAR)

        val userName = column<String>(name = "user_name", jdbcType = JDBCType.VARCHAR)

        val emailAddress = column<String>(name = "email_address", jdbcType = JDBCType.VARCHAR)

        val authority = column<String>(name = "authority", jdbcType = JDBCType.CHAR)

        val userStatus = column<String>(name = "user_status", jdbcType = JDBCType.CHAR)

        val lastLoginDatetime = column<LocalDateTime>(name = "last_login_datetime", jdbcType = JDBCType.TIMESTAMP)

        val registrationUserId = column<Long>(name = "registration_user_id", jdbcType = JDBCType.BIGINT)

        val registrationDatetime = column<LocalDateTime>(name = "registration_datetime", jdbcType = JDBCType.TIMESTAMP)

        val updateUserId = column<Long>(name = "update_user_id", jdbcType = JDBCType.BIGINT)

        val updateDatetime = column<LocalDateTime>(name = "update_datetime", jdbcType = JDBCType.TIMESTAMP)

        val createBy = column<String>(name = "create_by", jdbcType = JDBCType.VARCHAR)

        val createAt = column<LocalDateTime>(name = "create_at", jdbcType = JDBCType.TIMESTAMP)

        val updateBy = column<String>(name = "update_by", jdbcType = JDBCType.VARCHAR)

        val updateAt = column<LocalDateTime>(name = "update_at", jdbcType = JDBCType.TIMESTAMP)
    }
}