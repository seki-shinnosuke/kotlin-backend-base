/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.mapper

import java.sql.JDBCType
import java.time.LocalDateTime
import org.mybatis.dynamic.sql.AliasableSqlTable
import org.mybatis.dynamic.sql.util.kotlin.elements.column

object EmailSendDynamicSqlSupport {
    val emailSend = EmailSend()

    val emailSendId = emailSend.emailSendId

    val sendUserId = emailSend.sendUserId

    val sendEmailAddress = emailSend.sendEmailAddress

    val emailTitle = emailSend.emailTitle

    val sendReservationDatetime = emailSend.sendReservationDatetime

    val sendCompletedDatetime = emailSend.sendCompletedDatetime

    val sendStatus = emailSend.sendStatus

    val createBy = emailSend.createBy

    val createAt = emailSend.createAt

    val updateBy = emailSend.updateBy

    val updateAt = emailSend.updateAt

    val emailText = emailSend.emailText

    class EmailSend : AliasableSqlTable<EmailSend>("email_send", ::EmailSend) {
        val emailSendId = column<Long>(name = "email_send_id", jdbcType = JDBCType.BIGINT)

        val sendUserId = column<Long>(name = "send_user_id", jdbcType = JDBCType.BIGINT)

        val sendEmailAddress = column<String>(name = "send_email_address", jdbcType = JDBCType.VARCHAR)

        val emailTitle = column<String>(name = "email_title", jdbcType = JDBCType.VARCHAR)

        val sendReservationDatetime = column<LocalDateTime>(name = "send_reservation_datetime", jdbcType = JDBCType.TIMESTAMP)

        val sendCompletedDatetime = column<LocalDateTime>(name = "send_completed_datetime", jdbcType = JDBCType.TIMESTAMP)

        val sendStatus = column<String>(name = "send_status", jdbcType = JDBCType.CHAR)

        val createBy = column<String>(name = "create_by", jdbcType = JDBCType.VARCHAR)

        val createAt = column<LocalDateTime>(name = "create_at", jdbcType = JDBCType.TIMESTAMP)

        val updateBy = column<String>(name = "update_by", jdbcType = JDBCType.VARCHAR)

        val updateAt = column<LocalDateTime>(name = "update_at", jdbcType = JDBCType.TIMESTAMP)

        val emailText = column<String>(name = "email_text", jdbcType = JDBCType.LONGVARCHAR)
    }
}