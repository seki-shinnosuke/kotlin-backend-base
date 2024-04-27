package seki.kotlin.backend.base.common.extension

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * 日付を文字列に置換する
 */
fun LocalDate.toFormatString(format: String = "uuuu-MM-dd"): String = DateTimeFormatter.ofPattern(format).format(this)

/**
 * 日付を年月に置換する
 */
fun LocalDate.toFormatYearMonth(): YearMonth = YearMonth.of(this.year, this.month)

/**
 * 同日を含めた以前判定
 */
fun LocalDate.isBeforeEqual(target: LocalDate?): Boolean {
    if (target == null) {
        return false
    }
    return this.isEqual(target) || this.isBefore(target)
}

/**
 * 同日を含めた以降判定
 */
fun LocalDate.isAfterEqual(target: LocalDate?): Boolean {
    if (target == null) {
        return false
    }
    return this.isEqual(target) || this.isAfter(target)
}
