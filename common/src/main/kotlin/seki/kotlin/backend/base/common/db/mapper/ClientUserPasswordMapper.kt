/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.mapper

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.SelectProvider
import org.apache.ibatis.type.JdbcType
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
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.insertMultiple
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectDistinct
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectList
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectOne
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.update
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper
import seki.kotlin.backend.base.common.db.mapper.ClientUserPasswordDynamicSqlSupport.clientUserId
import seki.kotlin.backend.base.common.db.mapper.ClientUserPasswordDynamicSqlSupport.clientUserPassword
import seki.kotlin.backend.base.common.db.mapper.ClientUserPasswordDynamicSqlSupport.createAt
import seki.kotlin.backend.base.common.db.mapper.ClientUserPasswordDynamicSqlSupport.createBy
import seki.kotlin.backend.base.common.db.mapper.ClientUserPasswordDynamicSqlSupport.password
import seki.kotlin.backend.base.common.db.mapper.ClientUserPasswordDynamicSqlSupport.passwordStatus
import seki.kotlin.backend.base.common.db.mapper.ClientUserPasswordDynamicSqlSupport.temporaryPassword
import seki.kotlin.backend.base.common.db.mapper.ClientUserPasswordDynamicSqlSupport.temporaryPasswordExpiryDatetime
import seki.kotlin.backend.base.common.db.mapper.ClientUserPasswordDynamicSqlSupport.updateAt
import seki.kotlin.backend.base.common.db.mapper.ClientUserPasswordDynamicSqlSupport.updateBy
import seki.kotlin.backend.base.common.db.model.ClientUserPassword

@Mapper
interface ClientUserPasswordMapper : CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<ClientUserPassword>, CommonUpdateMapper {
    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @Results(id="ClientUserPasswordResult", value = [
        Result(column="client_user_id", property="clientUserId", jdbcType=JdbcType.BIGINT, id=true),
        Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        Result(column="password_status", property="passwordStatus", jdbcType=JdbcType.CHAR),
        Result(column="temporary_password", property="temporaryPassword", jdbcType=JdbcType.VARCHAR),
        Result(column="temporary_password_expiry_datetime", property="temporaryPasswordExpiryDatetime", jdbcType=JdbcType.TIMESTAMP),
        Result(column="create_by", property="createBy", jdbcType=JdbcType.VARCHAR),
        Result(column="create_at", property="createAt", jdbcType=JdbcType.TIMESTAMP),
        Result(column="update_by", property="updateBy", jdbcType=JdbcType.VARCHAR),
        Result(column="update_at", property="updateAt", jdbcType=JdbcType.TIMESTAMP)
    ])
    fun selectMany(selectStatement: SelectStatementProvider): List<ClientUserPassword>

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @ResultMap("ClientUserPasswordResult")
    fun selectOne(selectStatement: SelectStatementProvider): ClientUserPassword?
}

fun ClientUserPasswordMapper.count(completer: CountCompleter) =
    countFrom(this::count, clientUserPassword, completer)

fun ClientUserPasswordMapper.delete(completer: DeleteCompleter) =
    deleteFrom(this::delete, clientUserPassword, completer)

fun ClientUserPasswordMapper.deleteByPrimaryKey(clientUserId_: Long) =
    delete {
        where { clientUserId isEqualTo clientUserId_ }
    }

fun ClientUserPasswordMapper.insert(row: ClientUserPassword) =
    insert(this::insert, row, clientUserPassword) {
        map(clientUserId) toProperty "clientUserId"
        map(password) toProperty "password"
        map(passwordStatus) toProperty "passwordStatus"
        map(temporaryPassword) toProperty "temporaryPassword"
        map(temporaryPasswordExpiryDatetime) toProperty "temporaryPasswordExpiryDatetime"
        map(createBy) toProperty "createBy"
        map(createAt) toProperty "createAt"
        map(updateBy) toProperty "updateBy"
        map(updateAt) toProperty "updateAt"
    }

fun ClientUserPasswordMapper.insertMultiple(records: Collection<ClientUserPassword>) =
    insertMultiple(this::insertMultiple, records, clientUserPassword) {
        map(clientUserId) toProperty "clientUserId"
        map(password) toProperty "password"
        map(passwordStatus) toProperty "passwordStatus"
        map(temporaryPassword) toProperty "temporaryPassword"
        map(temporaryPasswordExpiryDatetime) toProperty "temporaryPasswordExpiryDatetime"
        map(createBy) toProperty "createBy"
        map(createAt) toProperty "createAt"
        map(updateBy) toProperty "updateBy"
        map(updateAt) toProperty "updateAt"
    }

fun ClientUserPasswordMapper.insertMultiple(vararg records: ClientUserPassword) =
    insertMultiple(records.toList())

fun ClientUserPasswordMapper.insertSelective(row: ClientUserPassword) =
    insert(this::insert, row, clientUserPassword) {
        map(clientUserId).toPropertyWhenPresent("clientUserId", row::clientUserId)
        map(password).toPropertyWhenPresent("password", row::password)
        map(passwordStatus).toPropertyWhenPresent("passwordStatus", row::passwordStatus)
        map(temporaryPassword).toPropertyWhenPresent("temporaryPassword", row::temporaryPassword)
        map(temporaryPasswordExpiryDatetime).toPropertyWhenPresent("temporaryPasswordExpiryDatetime", row::temporaryPasswordExpiryDatetime)
        map(createBy).toPropertyWhenPresent("createBy", row::createBy)
        map(createAt).toPropertyWhenPresent("createAt", row::createAt)
        map(updateBy).toPropertyWhenPresent("updateBy", row::updateBy)
        map(updateAt).toPropertyWhenPresent("updateAt", row::updateAt)
    }

private val columnList = listOf(clientUserId, password, passwordStatus, temporaryPassword, temporaryPasswordExpiryDatetime, createBy, createAt, updateBy, updateAt)

fun ClientUserPasswordMapper.selectOne(completer: SelectCompleter) =
    selectOne(this::selectOne, columnList, clientUserPassword, completer)

fun ClientUserPasswordMapper.select(completer: SelectCompleter) =
    selectList(this::selectMany, columnList, clientUserPassword, completer)

fun ClientUserPasswordMapper.selectDistinct(completer: SelectCompleter) =
    selectDistinct(this::selectMany, columnList, clientUserPassword, completer)

fun ClientUserPasswordMapper.selectByPrimaryKey(clientUserId_: Long) =
    selectOne {
        where { clientUserId isEqualTo clientUserId_ }
    }

fun ClientUserPasswordMapper.update(completer: UpdateCompleter) =
    update(this::update, clientUserPassword, completer)

fun KotlinUpdateBuilder.updateAllColumns(row: ClientUserPassword) =
    apply {
        set(clientUserId) equalToOrNull row::clientUserId
        set(password) equalToOrNull row::password
        set(passwordStatus) equalToOrNull row::passwordStatus
        set(temporaryPassword) equalToOrNull row::temporaryPassword
        set(temporaryPasswordExpiryDatetime) equalToOrNull row::temporaryPasswordExpiryDatetime
        set(createBy) equalToOrNull row::createBy
        set(createAt) equalToOrNull row::createAt
        set(updateBy) equalToOrNull row::updateBy
        set(updateAt) equalToOrNull row::updateAt
    }

fun KotlinUpdateBuilder.updateSelectiveColumns(row: ClientUserPassword) =
    apply {
        set(clientUserId) equalToWhenPresent row::clientUserId
        set(password) equalToWhenPresent row::password
        set(passwordStatus) equalToWhenPresent row::passwordStatus
        set(temporaryPassword) equalToWhenPresent row::temporaryPassword
        set(temporaryPasswordExpiryDatetime) equalToWhenPresent row::temporaryPasswordExpiryDatetime
        set(createBy) equalToWhenPresent row::createBy
        set(createAt) equalToWhenPresent row::createAt
        set(updateBy) equalToWhenPresent row::updateBy
        set(updateAt) equalToWhenPresent row::updateAt
    }

fun ClientUserPasswordMapper.updateByPrimaryKey(row: ClientUserPassword) =
    update {
        set(password) equalToOrNull row::password
        set(passwordStatus) equalToOrNull row::passwordStatus
        set(temporaryPassword) equalToOrNull row::temporaryPassword
        set(temporaryPasswordExpiryDatetime) equalToOrNull row::temporaryPasswordExpiryDatetime
        set(createBy) equalToOrNull row::createBy
        set(createAt) equalToOrNull row::createAt
        set(updateBy) equalToOrNull row::updateBy
        set(updateAt) equalToOrNull row::updateAt
        where { clientUserId isEqualTo row.clientUserId!! }
    }

fun ClientUserPasswordMapper.updateByPrimaryKeySelective(row: ClientUserPassword) =
    update {
        set(password) equalToWhenPresent row::password
        set(passwordStatus) equalToWhenPresent row::passwordStatus
        set(temporaryPassword) equalToWhenPresent row::temporaryPassword
        set(temporaryPasswordExpiryDatetime) equalToWhenPresent row::temporaryPasswordExpiryDatetime
        set(createBy) equalToWhenPresent row::createBy
        set(createAt) equalToWhenPresent row::createAt
        set(updateBy) equalToWhenPresent row::updateBy
        set(updateAt) equalToWhenPresent row::updateAt
        where { clientUserId isEqualTo row.clientUserId!! }
    }