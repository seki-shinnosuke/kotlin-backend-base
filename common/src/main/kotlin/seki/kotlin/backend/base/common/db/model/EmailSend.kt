/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.model

import java.time.LocalDateTime

data class EmailSend(
    var emailSendId: Long? = null,
    var sendUserId: Long? = null,
    var sendEmailAddress: String? = null,
    var emailTitle: String? = null,
    var sendReservationDatetime: LocalDateTime? = null,
    var sendCompletedDatetime: LocalDateTime? = null,
    var sendStatus: String? = null,
    var createBy: String? = null,
    var createAt: LocalDateTime? = null,
    var updateBy: String? = null,
    var updateAt: LocalDateTime? = null,
    var emailText: String? = null
)