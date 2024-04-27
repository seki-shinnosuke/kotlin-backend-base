package seki.kotlin.backend.base.common.extension

import org.apache.commons.lang3.StringUtils
import java.nio.charset.Charset
import java.text.MessageFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import java.util.Base64
import java.util.Locale
import java.util.stream.Collectors

/**
 * 文字列を日時に置換する
 */
fun String.toFormatLocalDateTime(format: String = "uuuu-MM-dd HH:mm:ss"): LocalDateTime =
    LocalDateTime.parse(this, DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT))

/**
 * 文字列を日付に置換する
 */
fun String.toFormatLocalDate(format: String = "uuuu-MM-dd"): LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT))

/**
 * "uuuu-MM-dd HH:mm:ss"形式かどうかを返す
 */
fun String.isUUUUMMddHHmmssHyphenFormat(): Boolean {
    return runCatching {
        this.toFormatLocalDateTime()
    }.isSuccess
}

/**
 * MessageFormatでの変換結果を返す
 */
fun String.messageFormat(vararg arguments: Any?): String {
    return MessageFormat.format(this, *arguments)
}

/**
 * "uuuu-MM-dd"形式かどうかを返す
 */
fun String.isUUUUMMddHyphenFormat(): Boolean {
    return runCatching {
        this.toFormatLocalDate()
    }.isSuccess
}

/**
 * キャメルケースをDBカラム用のスネークケースに変換して返す
 */
fun String.toColumnName(): String {
    return StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(this), "_").lowercase(Locale.getDefault())
}

/**
 * Base64文字列をByteArrayに置換する
 */
fun String.base64ToBytes(): ByteArray = Base64.getDecoder().decode(this)

/**
 * 指定文字列を文字化け対応したWindows-31jに変換する
 *
 * csvファイル出力時の文字コード変換にて特定文字の文字化けが発生するため個別実装する(Shift_JISに含まれていない漢字などを利用したいため)
 * 理由: 元の文字コードに対しWindows-31jに変換する際は中間文字コードとしてUnicodeを利用しており文字コードポイントの相違が発生してしまうケースがある
 */
fun String.toWindows31jByteArray(): ByteArray {
    val convertString =
        this.chars().mapToObj { i ->
            val convertChar =
                when (i) {
                    // 全角チルダ（波ダッシュ）
                    0x301C -> 0xFF5E
                    // 全角マイナス
                    0x2212 -> 0xFF0D
                    // セント
                    0x00A2 -> 0xFFE0
                    // ポンド
                    0x00A3 -> 0xFFE1
                    // 否定（全角）
                    0x00AC -> 0xFFE2
                    // ダッシュ（全角）
                    0x2014 -> 0x2015
                    // 平行記号
                    0x2016 -> 0x2225
                    // 以外は何もせず返す
                    else -> i
                }
            convertChar.toChar().toString()
        }.collect(Collectors.joining())
    return convertString.toByteArray(Charset.forName("windows-31j"))
}
