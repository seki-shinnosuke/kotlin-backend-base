/*
 * Auto-generated file. Created by MyBatis Generator
 */
package seki.kotlin.backend.base.common.db.mapper

import java.time.LocalDateTime
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
import seki.kotlin.backend.base.common.db.mapper.VirtualDatetimeManagementDynamicSqlSupport.registrationDatetime
import seki.kotlin.backend.base.common.db.mapper.VirtualDatetimeManagementDynamicSqlSupport.virtualDatetime
import seki.kotlin.backend.base.common.db.mapper.VirtualDatetimeManagementDynamicSqlSupport.virtualDatetimeManagement
import seki.kotlin.backend.base.common.db.model.VirtualDatetimeManagement

@Mapper
interface VirtualDatetimeManagementMapper : CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<VirtualDatetimeManagement>, CommonUpdateMapper {
    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @Results(id="VirtualDatetimeManagementResult", value = [
        Result(column="virtual_datetime", property="virtualDatetime", jdbcType=JdbcType.TIMESTAMP, id=true),
        Result(column="registration_datetime", property="registrationDatetime", jdbcType=JdbcType.TIMESTAMP)
    ])
    fun selectMany(selectStatement: SelectStatementProvider): List<VirtualDatetimeManagement>

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @ResultMap("VirtualDatetimeManagementResult")
    fun selectOne(selectStatement: SelectStatementProvider): VirtualDatetimeManagement?
}

fun VirtualDatetimeManagementMapper.count(completer: CountCompleter) =
    countFrom(this::count, virtualDatetimeManagement, completer)

fun VirtualDatetimeManagementMapper.delete(completer: DeleteCompleter) =
    deleteFrom(this::delete, virtualDatetimeManagement, completer)

fun VirtualDatetimeManagementMapper.deleteByPrimaryKey(virtualDatetime_: LocalDateTime) =
    delete {
        where { virtualDatetime isEqualTo virtualDatetime_ }
    }

fun VirtualDatetimeManagementMapper.insert(row: VirtualDatetimeManagement) =
    insert(this::insert, row, virtualDatetimeManagement) {
        map(virtualDatetime) toProperty "virtualDatetime"
        map(registrationDatetime) toProperty "registrationDatetime"
    }

fun VirtualDatetimeManagementMapper.insertMultiple(records: Collection<VirtualDatetimeManagement>) =
    insertMultiple(this::insertMultiple, records, virtualDatetimeManagement) {
        map(virtualDatetime) toProperty "virtualDatetime"
        map(registrationDatetime) toProperty "registrationDatetime"
    }

fun VirtualDatetimeManagementMapper.insertMultiple(vararg records: VirtualDatetimeManagement) =
    insertMultiple(records.toList())

fun VirtualDatetimeManagementMapper.insertSelective(row: VirtualDatetimeManagement) =
    insert(this::insert, row, virtualDatetimeManagement) {
        map(virtualDatetime).toPropertyWhenPresent("virtualDatetime", row::virtualDatetime)
        map(registrationDatetime).toPropertyWhenPresent("registrationDatetime", row::registrationDatetime)
    }

private val columnList = listOf(virtualDatetime, registrationDatetime)

fun VirtualDatetimeManagementMapper.selectOne(completer: SelectCompleter) =
    selectOne(this::selectOne, columnList, virtualDatetimeManagement, completer)

fun VirtualDatetimeManagementMapper.select(completer: SelectCompleter) =
    selectList(this::selectMany, columnList, virtualDatetimeManagement, completer)

fun VirtualDatetimeManagementMapper.selectDistinct(completer: SelectCompleter) =
    selectDistinct(this::selectMany, columnList, virtualDatetimeManagement, completer)

fun VirtualDatetimeManagementMapper.selectByPrimaryKey(virtualDatetime_: LocalDateTime) =
    selectOne {
        where { virtualDatetime isEqualTo virtualDatetime_ }
    }

fun VirtualDatetimeManagementMapper.update(completer: UpdateCompleter) =
    update(this::update, virtualDatetimeManagement, completer)

fun KotlinUpdateBuilder.updateAllColumns(row: VirtualDatetimeManagement) =
    apply {
        set(virtualDatetime) equalToOrNull row::virtualDatetime
        set(registrationDatetime) equalToOrNull row::registrationDatetime
    }

fun KotlinUpdateBuilder.updateSelectiveColumns(row: VirtualDatetimeManagement) =
    apply {
        set(virtualDatetime) equalToWhenPresent row::virtualDatetime
        set(registrationDatetime) equalToWhenPresent row::registrationDatetime
    }

fun VirtualDatetimeManagementMapper.updateByPrimaryKey(row: VirtualDatetimeManagement) =
    update {
        set(registrationDatetime) equalToOrNull row::registrationDatetime
        where { virtualDatetime isEqualTo row.virtualDatetime!! }
    }

fun VirtualDatetimeManagementMapper.updateByPrimaryKeySelective(row: VirtualDatetimeManagement) =
    update {
        set(registrationDatetime) equalToWhenPresent row::registrationDatetime
        where { virtualDatetime isEqualTo row.virtualDatetime!! }
    }