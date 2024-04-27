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
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementDynamicSqlSupport.accessDatetime
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementDynamicSqlSupport.accessLimitDatetime
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementDynamicSqlSupport.accessLogId
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementDynamicSqlSupport.accessLogManagement
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementDynamicSqlSupport.accessType
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementDynamicSqlSupport.createAt
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementDynamicSqlSupport.createBy
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementDynamicSqlSupport.ipAddress
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementDynamicSqlSupport.updateAt
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementDynamicSqlSupport.updateBy
import seki.kotlin.backend.base.common.db.mapper.AccessLogManagementDynamicSqlSupport.userAgent
import seki.kotlin.backend.base.common.db.model.AccessLogManagement

@Mapper
interface AccessLogManagementMapper : CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    @InsertProvider(type=SqlProviderAdapter::class, method="insert")
    @SelectKey(statement=["SELECT LAST_INSERT_ID()"], keyProperty="row.accessLogId", before=false, resultType=Long::class)
    fun insert(insertStatement: InsertStatementProvider<AccessLogManagement>): Int

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @Results(id="AccessLogManagementResult", value = [
        Result(column="access_log_id", property="accessLogId", jdbcType=JdbcType.BIGINT, id=true),
        Result(column="ip_address", property="ipAddress", jdbcType=JdbcType.VARCHAR),
        Result(column="access_type", property="accessType", jdbcType=JdbcType.VARCHAR),
        Result(column="access_datetime", property="accessDatetime", jdbcType=JdbcType.TIMESTAMP),
        Result(column="user_agent", property="userAgent", jdbcType=JdbcType.VARCHAR),
        Result(column="access_limit_datetime", property="accessLimitDatetime", jdbcType=JdbcType.TIMESTAMP),
        Result(column="create_by", property="createBy", jdbcType=JdbcType.VARCHAR),
        Result(column="create_at", property="createAt", jdbcType=JdbcType.TIMESTAMP),
        Result(column="update_by", property="updateBy", jdbcType=JdbcType.VARCHAR),
        Result(column="update_at", property="updateAt", jdbcType=JdbcType.TIMESTAMP)
    ])
    fun selectMany(selectStatement: SelectStatementProvider): List<AccessLogManagement>

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @ResultMap("AccessLogManagementResult")
    fun selectOne(selectStatement: SelectStatementProvider): AccessLogManagement?
}

fun AccessLogManagementMapper.count(completer: CountCompleter) =
    countFrom(this::count, accessLogManagement, completer)

fun AccessLogManagementMapper.delete(completer: DeleteCompleter) =
    deleteFrom(this::delete, accessLogManagement, completer)

fun AccessLogManagementMapper.deleteByPrimaryKey(accessLogId_: Long) =
    delete {
        where { accessLogId isEqualTo accessLogId_ }
    }

fun AccessLogManagementMapper.insert(row: AccessLogManagement) =
    insert(this::insert, row, accessLogManagement) {
        map(ipAddress) toProperty "ipAddress"
        map(accessType) toProperty "accessType"
        map(accessDatetime) toProperty "accessDatetime"
        map(userAgent) toProperty "userAgent"
        map(accessLimitDatetime) toProperty "accessLimitDatetime"
        map(createBy) toProperty "createBy"
        map(createAt) toProperty "createAt"
        map(updateBy) toProperty "updateBy"
        map(updateAt) toProperty "updateAt"
    }

fun AccessLogManagementMapper.insertSelective(row: AccessLogManagement) =
    insert(this::insert, row, accessLogManagement) {
        map(ipAddress).toPropertyWhenPresent("ipAddress", row::ipAddress)
        map(accessType).toPropertyWhenPresent("accessType", row::accessType)
        map(accessDatetime).toPropertyWhenPresent("accessDatetime", row::accessDatetime)
        map(userAgent).toPropertyWhenPresent("userAgent", row::userAgent)
        map(accessLimitDatetime).toPropertyWhenPresent("accessLimitDatetime", row::accessLimitDatetime)
        map(createBy).toPropertyWhenPresent("createBy", row::createBy)
        map(createAt).toPropertyWhenPresent("createAt", row::createAt)
        map(updateBy).toPropertyWhenPresent("updateBy", row::updateBy)
        map(updateAt).toPropertyWhenPresent("updateAt", row::updateAt)
    }

private val columnList = listOf(accessLogId, ipAddress, accessType, accessDatetime, userAgent, accessLimitDatetime, createBy, createAt, updateBy, updateAt)

fun AccessLogManagementMapper.selectOne(completer: SelectCompleter) =
    selectOne(this::selectOne, columnList, accessLogManagement, completer)

fun AccessLogManagementMapper.select(completer: SelectCompleter) =
    selectList(this::selectMany, columnList, accessLogManagement, completer)

fun AccessLogManagementMapper.selectDistinct(completer: SelectCompleter) =
    selectDistinct(this::selectMany, columnList, accessLogManagement, completer)

fun AccessLogManagementMapper.selectByPrimaryKey(accessLogId_: Long) =
    selectOne {
        where { accessLogId isEqualTo accessLogId_ }
    }

fun AccessLogManagementMapper.update(completer: UpdateCompleter) =
    update(this::update, accessLogManagement, completer)

fun KotlinUpdateBuilder.updateAllColumns(row: AccessLogManagement) =
    apply {
        set(ipAddress) equalToOrNull row::ipAddress
        set(accessType) equalToOrNull row::accessType
        set(accessDatetime) equalToOrNull row::accessDatetime
        set(userAgent) equalToOrNull row::userAgent
        set(accessLimitDatetime) equalToOrNull row::accessLimitDatetime
        set(createBy) equalToOrNull row::createBy
        set(createAt) equalToOrNull row::createAt
        set(updateBy) equalToOrNull row::updateBy
        set(updateAt) equalToOrNull row::updateAt
    }

fun KotlinUpdateBuilder.updateSelectiveColumns(row: AccessLogManagement) =
    apply {
        set(ipAddress) equalToWhenPresent row::ipAddress
        set(accessType) equalToWhenPresent row::accessType
        set(accessDatetime) equalToWhenPresent row::accessDatetime
        set(userAgent) equalToWhenPresent row::userAgent
        set(accessLimitDatetime) equalToWhenPresent row::accessLimitDatetime
        set(createBy) equalToWhenPresent row::createBy
        set(createAt) equalToWhenPresent row::createAt
        set(updateBy) equalToWhenPresent row::updateBy
        set(updateAt) equalToWhenPresent row::updateAt
    }

fun AccessLogManagementMapper.updateByPrimaryKey(row: AccessLogManagement) =
    update {
        set(ipAddress) equalToOrNull row::ipAddress
        set(accessType) equalToOrNull row::accessType
        set(accessDatetime) equalToOrNull row::accessDatetime
        set(userAgent) equalToOrNull row::userAgent
        set(accessLimitDatetime) equalToOrNull row::accessLimitDatetime
        set(createBy) equalToOrNull row::createBy
        set(createAt) equalToOrNull row::createAt
        set(updateBy) equalToOrNull row::updateBy
        set(updateAt) equalToOrNull row::updateAt
        where { accessLogId isEqualTo row.accessLogId!! }
    }

fun AccessLogManagementMapper.updateByPrimaryKeySelective(row: AccessLogManagement) =
    update {
        set(ipAddress) equalToWhenPresent row::ipAddress
        set(accessType) equalToWhenPresent row::accessType
        set(accessDatetime) equalToWhenPresent row::accessDatetime
        set(userAgent) equalToWhenPresent row::userAgent
        set(accessLimitDatetime) equalToWhenPresent row::accessLimitDatetime
        set(createBy) equalToWhenPresent row::createBy
        set(createAt) equalToWhenPresent row::createAt
        set(updateBy) equalToWhenPresent row::updateBy
        set(updateAt) equalToWhenPresent row::updateAt
        where { accessLogId isEqualTo row.accessLogId!! }
    }