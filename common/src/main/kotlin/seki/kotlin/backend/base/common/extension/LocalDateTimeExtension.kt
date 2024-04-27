package seki.kotlin.backend.base.common.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * 日時を文字列に置換する
 */
fun LocalDateTime.toFormatString(format: String = "uuuu-MM-dd HH:mm:ss"): String = DateTimeFormatter.ofPattern(format).format(this)

/**
 * 同日時を含めた以前判定
 */
fun LocalDateTime.isBeforeEqual(target: LocalDateTime?): Boolean {
    if (target == null) {
        return false
    }
    return this.isEqual(target) || this.isBefore(target)
}

/**
 * 同日時を含めた以後判定
 */
fun LocalDateTime.isAfterEqual(target: LocalDateTime?): Boolean {
    if (target == null) {
        return false
    }
    return this.isEqual(target) || this.isAfter(target)
}

/**
 * 指定の単位を切り捨て日時を比較する
 */
fun LocalDateTime.isEqualTruncated(
    target: LocalDateTime?,
    chronoUnit: ChronoUnit = ChronoUnit.SECONDS,
): Boolean {
    if (target == null) {
        return false
    }
    return this.truncatedTo(chronoUnit).isEqual(target.truncatedTo(chronoUnit))
}
