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
import seki.kotlin.backend.base.common.db.mapper.BatchStartControlDynamicSqlSupport.batchId
import seki.kotlin.backend.base.common.db.mapper.BatchStartControlDynamicSqlSupport.batchStartControl
import seki.kotlin.backend.base.common.db.mapper.BatchStartControlDynamicSqlSupport.createAt
import seki.kotlin.backend.base.common.db.mapper.BatchStartControlDynamicSqlSupport.createBy
import seki.kotlin.backend.base.common.db.mapper.BatchStartControlDynamicSqlSupport.startDatetime
import seki.kotlin.backend.base.common.db.mapper.BatchStartControlDynamicSqlSupport.updateAt
import seki.kotlin.backend.base.common.db.mapper.BatchStartControlDynamicSqlSupport.updateBy
import seki.kotlin.backend.base.common.db.model.BatchStartControl

@Mapper
interface BatchStartControlMapper : CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<BatchStartControl>, CommonUpdateMapper {
    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @Results(id="BatchStartControlResult", value = [
        Result(column="batch_id", property="batchId", jdbcType=JdbcType.VARCHAR, id=true),
        Result(column="start_datetime", property="startDatetime", jdbcType=JdbcType.TIMESTAMP),
        Result(column="create_by", property="createBy", jdbcType=JdbcType.VARCHAR),
        Result(column="create_at", property="createAt", jdbcType=JdbcType.TIMESTAMP),
        Result(column="update_by", property="updateBy", jdbcType=JdbcType.VARCHAR),
        Result(column="update_at", property="updateAt", jdbcType=JdbcType.TIMESTAMP)
    ])
    fun selectMany(selectStatement: SelectStatementProvider): List<BatchStartControl>

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @ResultMap("BatchStartControlResult")
    fun selectOne(selectStatement: SelectStatementProvider): BatchStartControl?
}

fun BatchStartControlMapper.count(completer: CountCompleter) =
    countFrom(this::count, batchStartControl, completer)

fun BatchStartControlMapper.delete(completer: DeleteCompleter) =
    deleteFrom(this::delete, batchStartControl, completer)

fun BatchStartControlMapper.deleteByPrimaryKey(batchId_: String) =
    delete {
        where { batchId isEqualTo batchId_ }
    }

fun BatchStartControlMapper.insert(row: BatchStartControl) =
    insert(this::insert, row, batchStartControl) {
        map(batchId) toProperty "batchId"
        map(startDatetime) toProperty "startDatetime"
        map(createBy) toProperty "createBy"
        map(createAt) toProperty "createAt"
        map(updateBy) toProperty "updateBy"
        map(updateAt) toProperty "updateAt"
    }

fun BatchStartControlMapper.insertMultiple(records: Collection<BatchStartControl>) =
    insertMultiple(this::insertMultiple, records, batchStartControl) {
        map(batchId) toProperty "batchId"
        map(startDatetime) toProperty "startDatetime"
        map(createBy) toProperty "createBy"
        map(createAt) toProperty "createAt"
        map(updateBy) toProperty "updateBy"
        map(updateAt) toProperty "updateAt"
    }

fun BatchStartControlMapper.insertMultiple(vararg records: BatchStartControl) =
    insertMultiple(records.toList())

fun BatchStartControlMapper.insertSelective(row: BatchStartControl) =
    insert(this::insert, row, batchStartControl) {
        map(batchId).toPropertyWhenPresent("batchId", row::batchId)
        map(startDatetime).toPropertyWhenPresent("startDatetime", row::startDatetime)
        map(createBy).toPropertyWhenPresent("createBy", row::createBy)
        map(createAt).toPropertyWhenPresent("createAt", row::createAt)
        map(updateBy).toPropertyWhenPresent("updateBy", row::updateBy)
        map(updateAt).toPropertyWhenPresent("updateAt", row::updateAt)
    }

private val columnList = listOf(batchId, startDatetime, createBy, createAt, updateBy, updateAt)

fun BatchStartControlMapper.selectOne(completer: SelectCompleter) =
    selectOne(this::selectOne, columnList, batchStartControl, completer)

fun BatchStartControlMapper.select(completer: SelectCompleter) =
    selectList(this::selectMany, columnList, batchStartControl, completer)

fun BatchStartControlMapper.selectDistinct(completer: SelectCompleter) =
    selectDistinct(this::selectMany, columnList, batchStartControl, completer)

fun BatchStartControlMapper.selectByPrimaryKey(batchId_: String) =
    selectOne {
        where { batchId isEqualTo batchId_ }
    }

fun BatchStartControlMapper.update(completer: UpdateCompleter) =
    update(this::update, batchStartControl, completer)

fun KotlinUpdateBuilder.updateAllColumns(row: BatchStartControl) =
    apply {
        set(batchId) equalToOrNull row::batchId
        set(startDatetime) equalToOrNull row::startDatetime
        set(createBy) equalToOrNull row::createBy
        set(createAt) equalToOrNull row::createAt
        set(updateBy) equalToOrNull row::updateBy
        set(updateAt) equalToOrNull row::updateAt
    }

fun KotlinUpdateBuilder.updateSelectiveColumns(row: BatchStartControl) =
    apply {
        set(batchId) equalToWhenPresent row::batchId
        set(startDatetime) equalToWhenPresent row::startDatetime
        set(createBy) equalToWhenPresent row::createBy
        set(createAt) equalToWhenPresent row::createAt
        set(updateBy) equalToWhenPresent row::updateBy
        set(updateAt) equalToWhenPresent row::updateAt
    }

fun BatchStartControlMapper.updateByPrimaryKey(row: BatchStartControl) =
    update {
        set(startDatetime) equalToOrNull row::startDatetime
        set(createBy) equalToOrNull row::createBy
        set(createAt) equalToOrNull row::createAt
        set(updateBy) equalToOrNull row::updateBy
        set(updateAt) equalToOrNull row::updateAt
        where { batchId isEqualTo row.batchId!! }
    }

fun BatchStartControlMapper.updateByPrimaryKeySelective(row: BatchStartControl) =
    update {
        set(startDatetime) equalToWhenPresent row::startDatetime
        set(createBy) equalToWhenPresent row::createBy
        set(createAt) equalToWhenPresent row::createAt
        set(updateBy) equalToWhenPresent row::updateBy
        set(updateAt) equalToWhenPresent row::updateAt
        where { batchId isEqualTo row.batchId!! }
    }