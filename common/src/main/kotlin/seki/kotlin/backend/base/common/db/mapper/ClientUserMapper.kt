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
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.authority
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.clientUser
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.clientUserId
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.clientUserLoginId
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.createAt
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.createBy
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.emailAddress
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.lastLoginDatetime
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.registrationDatetime
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.registrationUserId
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.updateAt
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.updateBy
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.updateDatetime
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.updateUserId
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.userName
import seki.kotlin.backend.base.common.db.mapper.ClientUserDynamicSqlSupport.userStatus
import seki.kotlin.backend.base.common.db.model.ClientUser

@Mapper
interface ClientUserMapper : CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    @InsertProvider(type=SqlProviderAdapter::class, method="insert")
    @SelectKey(statement=["SELECT LAST_INSERT_ID()"], keyProperty="row.clientUserId", before=false, resultType=Long::class)
    fun insert(insertStatement: InsertStatementProvider<ClientUser>): Int

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @Results(id="ClientUserResult", value = [
        Result(column="client_user_id", property="clientUserId", jdbcType=JdbcType.BIGINT, id=true),
        Result(column="client_user_login_id", property="clientUserLoginId", jdbcType=JdbcType.VARCHAR),
        Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        Result(column="email_address", property="emailAddress", jdbcType=JdbcType.VARCHAR),
        Result(column="authority", property="authority", jdbcType=JdbcType.CHAR),
        Result(column="user_status", property="userStatus", jdbcType=JdbcType.CHAR),
        Result(column="last_login_datetime", property="lastLoginDatetime", jdbcType=JdbcType.TIMESTAMP),
        Result(column="registration_user_id", property="registrationUserId", jdbcType=JdbcType.BIGINT),
        Result(column="registration_datetime", property="registrationDatetime", jdbcType=JdbcType.TIMESTAMP),
        Result(column="update_user_id", property="updateUserId", jdbcType=JdbcType.BIGINT),
        Result(column="update_datetime", property="updateDatetime", jdbcType=JdbcType.TIMESTAMP),
        Result(column="create_by", property="createBy", jdbcType=JdbcType.VARCHAR),
        Result(column="create_at", property="createAt", jdbcType=JdbcType.TIMESTAMP),
        Result(column="update_by", property="updateBy", jdbcType=JdbcType.VARCHAR),
        Result(column="update_at", property="updateAt", jdbcType=JdbcType.TIMESTAMP)
    ])
    fun selectMany(selectStatement: SelectStatementProvider): List<ClientUser>

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @ResultMap("ClientUserResult")
    fun selectOne(selectStatement: SelectStatementProvider): ClientUser?
}

fun ClientUserMapper.count(completer: CountCompleter) =
    countFrom(this::count, clientUser, completer)

fun ClientUserMapper.delete(completer: DeleteCompleter) =
    deleteFrom(this::delete, clientUser, completer)

fun ClientUserMapper.deleteByPrimaryKey(clientUserId_: Long) =
    delete {
        where { clientUserId isEqualTo clientUserId_ }
    }

fun ClientUserMapper.insert(row: ClientUser) =
    insert(this::insert, row, clientUser) {
        map(clientUserLoginId) toProperty "clientUserLoginId"
        map(userName) toProperty "userName"
        map(emailAddress) toProperty "emailAddress"
        map(authority) toProperty "authority"
        map(userStatus) toProperty "userStatus"
        map(lastLoginDatetime) toProperty "lastLoginDatetime"
        map(registrationUserId) toProperty "registrationUserId"
        map(registrationDatetime) toProperty "registrationDatetime"
        map(updateUserId) toProperty "updateUserId"
        map(updateDatetime) toProperty "updateDatetime"
        map(createBy) toProperty "createBy"
        map(createAt) toProperty "createAt"
        map(updateBy) toProperty "updateBy"
        map(updateAt) toProperty "updateAt"
    }

fun ClientUserMapper.insertSelective(row: ClientUser) =
    insert(this::insert, row, clientUser) {
        map(clientUserLoginId).toPropertyWhenPresent("clientUserLoginId", row::clientUserLoginId)
        map(userName).toPropertyWhenPresent("userName", row::userName)
        map(emailAddress).toPropertyWhenPresent("emailAddress", row::emailAddress)
        map(authority).toPropertyWhenPresent("authority", row::authority)
        map(userStatus).toPropertyWhenPresent("userStatus", row::userStatus)
        map(lastLoginDatetime).toPropertyWhenPresent("lastLoginDatetime", row::lastLoginDatetime)
        map(registrationUserId).toPropertyWhenPresent("registrationUserId", row::registrationUserId)
        map(registrationDatetime).toPropertyWhenPresent("registrationDatetime", row::registrationDatetime)
        map(updateUserId).toPropertyWhenPresent("updateUserId", row::updateUserId)
        map(updateDatetime).toPropertyWhenPresent("updateDatetime", row::updateDatetime)
        map(createBy).toPropertyWhenPresent("createBy", row::createBy)
        map(createAt).toPropertyWhenPresent("createAt", row::createAt)
        map(updateBy).toPropertyWhenPresent("updateBy", row::updateBy)
        map(updateAt).toPropertyWhenPresent("updateAt", row::updateAt)
    }

private val columnList = listOf(clientUserId, clientUserLoginId, userName, emailAddress, authority, userStatus, lastLoginDatetime, registrationUserId, registrationDatetime, updateUserId, updateDatetime, createBy, createAt, updateBy, updateAt)

fun ClientUserMapper.selectOne(completer: SelectCompleter) =
    selectOne(this::selectOne, columnList, clientUser, completer)

fun ClientUserMapper.select(completer: SelectCompleter) =
    selectList(this::selectMany, columnList, clientUser, completer)

fun ClientUserMapper.selectDistinct(completer: SelectCompleter) =
    selectDistinct(this::selectMany, columnList, clientUser, completer)

fun ClientUserMapper.selectByPrimaryKey(clientUserId_: Long) =
    selectOne {
        where { clientUserId isEqualTo clientUserId_ }
    }

fun ClientUserMapper.update(completer: UpdateCompleter) =
    update(this::update, clientUser, completer)

fun KotlinUpdateBuilder.updateAllColumns(row: ClientUser) =
    apply {
        set(clientUserLoginId) equalToOrNull row::clientUserLoginId
        set(userName) equalToOrNull row::userName
        set(emailAddress) equalToOrNull row::emailAddress
        set(authority) equalToOrNull row::authority
        set(userStatus) equalToOrNull row::userStatus
        set(lastLoginDatetime) equalToOrNull row::lastLoginDatetime
        set(registrationUserId) equalToOrNull row::registrationUserId
        set(registrationDatetime) equalToOrNull row::registrationDatetime
        set(updateUserId) equalToOrNull row::updateUserId
        set(updateDatetime) equalToOrNull row::updateDatetime
        set(createBy) equalToOrNull row::createBy
        set(createAt) equalToOrNull row::createAt
        set(updateBy) equalToOrNull row::updateBy
        set(updateAt) equalToOrNull row::updateAt
    }

fun KotlinUpdateBuilder.updateSelectiveColumns(row: ClientUser) =
    apply {
        set(clientUserLoginId) equalToWhenPresent row::clientUserLoginId
        set(userName) equalToWhenPresent row::userName
        set(emailAddress) equalToWhenPresent row::emailAddress
        set(authority) equalToWhenPresent row::authority
        set(userStatus) equalToWhenPresent row::userStatus
        set(lastLoginDatetime) equalToWhenPresent row::lastLoginDatetime
        set(registrationUserId) equalToWhenPresent row::registrationUserId
        set(registrationDatetime) equalToWhenPresent row::registrationDatetime
        set(updateUserId) equalToWhenPresent row::updateUserId
        set(updateDatetime) equalToWhenPresent row::updateDatetime
        set(createBy) equalToWhenPresent row::createBy
        set(createAt) equalToWhenPresent row::createAt
        set(updateBy) equalToWhenPresent row::updateBy
        set(updateAt) equalToWhenPresent row::updateAt
    }

fun ClientUserMapper.updateByPrimaryKey(row: ClientUser) =
    update {
        set(clientUserLoginId) equalToOrNull row::clientUserLoginId
        set(userName) equalToOrNull row::userName
        set(emailAddress) equalToOrNull row::emailAddress
        set(authority) equalToOrNull row::authority
        set(userStatus) equalToOrNull row::userStatus
        set(lastLoginDatetime) equalToOrNull row::lastLoginDatetime
        set(registrationUserId) equalToOrNull row::registrationUserId
        set(registrationDatetime) equalToOrNull row::registrationDatetime
        set(updateUserId) equalToOrNull row::updateUserId
        set(updateDatetime) equalToOrNull row::updateDatetime
        set(createBy) equalToOrNull row::createBy
        set(createAt) equalToOrNull row::createAt
        set(updateBy) equalToOrNull row::updateBy
        set(updateAt) equalToOrNull row::updateAt
        where { clientUserId isEqualTo row.clientUserId!! }
    }

fun ClientUserMapper.updateByPrimaryKeySelective(row: ClientUser) =
    update {
        set(clientUserLoginId) equalToWhenPresent row::clientUserLoginId
        set(userName) equalToWhenPresent row::userName
        set(emailAddress) equalToWhenPresent row::emailAddress
        set(authority) equalToWhenPresent row::authority
        set(userStatus) equalToWhenPresent row::userStatus
        set(lastLoginDatetime) equalToWhenPresent row::lastLoginDatetime
        set(registrationUserId) equalToWhenPresent row::registrationUserId
        set(registrationDatetime) equalToWhenPresent row::registrationDatetime
        set(updateUserId) equalToWhenPresent row::updateUserId
        set(updateDatetime) equalToWhenPresent row::updateDatetime
        set(createBy) equalToWhenPresent row::createBy
        set(createAt) equalToWhenPresent row::createAt
        set(updateBy) equalToWhenPresent row::updateBy
        set(updateAt) equalToWhenPresent row::updateAt
        where { clientUserId isEqualTo row.clientUserId!! }
    }