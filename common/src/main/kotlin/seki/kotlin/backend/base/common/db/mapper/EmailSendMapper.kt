/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.mapper

import org.apache.ibatis.annotations.InsertProvider
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.SelectKey
import org.apache.ibatis.annotations.SelectProvider
import org.apache.ibatis.type.JdbcType
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider
import org.mybatis.dynamic.sql.util.SqlProviderAdapter
import org.mybatis.dynamic.sql.util.kotlin.CountCompleter
import org.mybatis.dynamic.sql.util.kotlin.DeleteCompleter
import org.mybatis.dynamic.sql.util.kotlin.KotlinUpdateBuilder
import org.mybatis.dynamic.sql.util.kotlin.SelectCompleter
import org.mybatis.dynamic.sql.util.kotlin.UpdateCompleter
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.countFrom
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.deleteFrom
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.insert
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectDistinct
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectList
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectOne
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.update
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.createAt
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.createBy
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.emailSend
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.emailSendId
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.emailText
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.emailTitle
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.sendCompletedDatetime
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.sendEmailAddress
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.sendReservationDatetime
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.sendStatus
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.sendUserId
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.updateAt
import seki.kotlin.backend.base.common.db.mapper.EmailSendDynamicSqlSupport.updateBy
import seki.kotlin.backend.base.common.db.model.EmailSend

@Mapper
interface EmailSendMapper : CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    @InsertProvider(type=SqlProviderAdapter::class, method="insert")
    @SelectKey(statement=["SELECT LAST_INSERT_ID()"], keyProperty="row.emailSendId", before=false, resultType=Long::class)
    fun insert(insertStatement: InsertStatementProvider<EmailSend>): Int

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @Results(id="EmailSendResult", value = [
        Result(column="email_send_id", property="emailSendId", jdbcType=JdbcType.BIGINT, id=true),
        Result(column="send_user_id", property="sendUserId", jdbcType=JdbcType.BIGINT),
        Result(column="send_email_address", property="sendEmailAddress", jdbcType=JdbcType.VARCHAR),
        Result(column="email_title", property="emailTitle", jdbcType=JdbcType.VARCHAR),
        Result(column="send_reservation_datetime", property="sendReservationDatetime", jdbcType=JdbcType.TIMESTAMP),
        Result(column="send_completed_datetime", property="sendCompletedDatetime", jdbcType=JdbcType.TIMESTAMP),
        Result(column="send_status", property="sendStatus", jdbcType=JdbcType.CHAR),
        Result(column="create_by", property="createBy", jdbcType=JdbcType.VARCHAR),
        Result(column="create_at", property="createAt", jdbcType=JdbcType.TIMESTAMP),
        Result(column="update_by", property="updateBy", jdbcType=JdbcType.VARCHAR),
        Result(column="update_at", property="updateAt", jdbcType=JdbcType.TIMESTAMP),
        Result(column="email_text", property="emailText", jdbcType=JdbcType.LONGVARCHAR)
    ])
    fun selectMany(selectStatement: SelectStatementProvider): List<EmailSend>

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @ResultMap("EmailSendResult")
    fun selectOne(selectStatement: SelectStatementProvider): EmailSend?
}

fun EmailSendMapper.count(completer: CountCompleter) =
    countFrom(this::count, emailSend, completer)

fun EmailSendMapper.delete(completer: DeleteCompleter) =
    deleteFrom(this::delete, emailSend, completer)

fun EmailSendMapper.deleteByPrimaryKey(emailSendId_: Long) =
    delete {
        where { emailSendId isEqualTo emailSendId_ }
    }

fun EmailSendMapper.insert(row: EmailSend) =
    insert(this::insert, row, emailSend) {
        map(sendUserId) toProperty "sendUserId"
        map(sendEmailAddress) toProperty "sendEmailAddress"
        map(emailTitle) toProperty "emailTitle"
        map(sendReservationDatetime) toProperty "sendReservationDatetime"
        map(sendCompletedDatetime) toProperty "sendCompletedDatetime"
        map(sendStatus) toProperty "sendStatus"
        map(createBy) toProperty "createBy"
        map(createAt) toProperty "createAt"
        map(updateBy) toProperty "updateBy"
        map(updateAt) toProperty "updateAt"
        map(emailText) toProperty "emailText"
    }

fun EmailSendMapper.insertSelective(row: EmailSend) =
    insert(this::insert, row, emailSend) {
        map(sendUserId).toPropertyWhenPresent("sendUserId", row::sendUserId)
        map(sendEmailAddress).toPropertyWhenPresent("sendEmailAddress", row::sendEmailAddress)
        map(emailTitle).toPropertyWhenPresent("emailTitle", row::emailTitle)
        map(sendReservationDatetime).toPropertyWhenPresent("sendReservationDatetime", row::sendReservationDatetime)
        map(sendCompletedDatetime).toPropertyWhenPresent("sendCompletedDatetime", row::sendCompletedDatetime)
        map(sendStatus).toPropertyWhenPresent("sendStatus", row::sendStatus)
        map(createBy).toPropertyWhenPresent("createBy", row::createBy)
        map(createAt).toPropertyWhenPresent("createAt", row::createAt)
        map(updateBy).toPropertyWhenPresent("updateBy", row::updateBy)
        map(updateAt).toPropertyWhenPresent("updateAt", row::updateAt)
        map(emailText).toPropertyWhenPresent("emailText", row::emailText)
    }

private val columnList = listOf(emailSendId, sendUserId, sendEmailAddress, emailTitle, sendReservationDatetime, sendCompletedDatetime, sendStatus, createBy, createAt, updateBy, updateAt, emailText)

fun EmailSendMapper.selectOne(completer: SelectCompleter) =
    selectOne(this::selectOne, columnList, emailSend, completer)

fun EmailSendMapper.select(completer: SelectCompleter) =
    selectList(this::selectMany, columnList, emailSend, completer)

fun EmailSendMapper.selectDistinct(completer: SelectCompleter) =
    selectDistinct(this::selectMany, columnList, emailSend, completer)

fun EmailSendMapper.selectByPrimaryKey(emailSendId_: Long) =
    selectOne {
        where { emailSendId isEqualTo emailSendId_ }
    }

fun EmailSendMapper.update(completer: UpdateCompleter) =
    update(this::update, emailSend, completer)

fun KotlinUpdateBuilder.updateAllColumns(row: EmailSend) =
    apply {
        set(sendUserId) equalToOrNull row::sendUserId
        set(sendEmailAddress) equalToOrNull row::sendEmailAddress
        set(emailTitle) equalToOrNull row::emailTitle
        set(sendReservationDatetime) equalToOrNull row::sendReservationDatetime
        set(sendCompletedDatetime) equalToOrNull row::sendCompletedDatetime
        set(sendStatus) equalToOrNull row::sendStatus
        set(createBy) equalToOrNull row::createBy
        set(createAt) equalToOrNull row::createAt
        set(updateBy) equalToOrNull row::updateBy
        set(updateAt) equalToOrNull row::updateAt
        set(emailText) equalToOrNull row::emailText
    }

fun KotlinUpdateBuilder.updateSelectiveColumns(row: EmailSend) =
    apply {
        set(sendUserId) equalToWhenPresent row::sendUserId
        set(sendEmailAddress) equalToWhenPresent row::sendEmailAddress
        set(emailTitle) equalToWhenPresent row::emailTitle
        set(sendReservationDatetime) equalToWhenPresent row::sendReservationDatetime
        set(sendCompletedDatetime) equalToWhenPresent row::sendCompletedDatetime
        set(sendStatus) equalToWhenPresent row::sendStatus
        set(createBy) equalToWhenPresent row::createBy
        set(createAt) equalToWhenPresent row::createAt
        set(updateBy) equalToWhenPresent row::updateBy
        set(updateAt) equalToWhenPresent row::updateAt
        set(emailText) equalToWhenPresent row::emailText
    }

fun EmailSendMapper.updateByPrimaryKey(row: EmailSend) =
    update {
        set(sendUserId) equalToOrNull row::sendUserId
        set(sendEmailAddress) equalToOrNull row::sendEmailAddress
        set(emailTitle) equalToOrNull row::emailTitle
        set(sendReservationDatetime) equalToOrNull row::sendReservationDatetime
        set(sendCompletedDatetime) equalToOrNull row::sendCompletedDatetime
        set(sendStatus) equalToOrNull row::sendStatus
        set(createBy) equalToOrNull row::createBy
        set(createAt) equalToOrNull row::createAt
        set(updateBy) equalToOrNull row::updateBy
        set(updateAt) equalToOrNull row::updateAt
        set(emailText) equalToOrNull row::emailText
        where { emailSendId isEqualTo row.emailSendId!! }
    }

fun EmailSendMapper.updateByPrimaryKeySelective(row: EmailSend) =
    update {
        set(sendUserId) equalToWhenPresent row::sendUserId
        set(sendEmailAddress) equalToWhenPresent row::sendEmailAddress
        set(emailTitle) equalToWhenPresent row::emailTitle
        set(sendReservationDatetime) equalToWhenPresent row::sendReservationDatetime
        set(sendCompletedDatetime) equalToWhenPresent row::sendCompletedDatetime
        set(sendStatus) equalToWhenPresent row::sendStatus
        set(createBy) equalToWhenPresent row::createBy
        set(createAt) equalToWhenPresent row::createAt
        set(updateBy) equalToWhenPresent row::updateBy
        set(updateAt) equalToWhenPresent row::updateAt
        set(emailText) equalToWhenPresent row::emailText
        where { emailSendId isEqualTo row.emailSendId!! }
    }