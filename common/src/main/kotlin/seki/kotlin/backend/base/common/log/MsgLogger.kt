package seki.kotlin.backend.base.common.log

import org.slf4j.Logger
import org.slf4j.Marker
import org.springframework.boot.logging.LogLevel

/**
 * 独自メッセージロガークラス
 */
class MsgLogger(
    private val originalLogger: Logger,
) : Logger by originalLogger {
    override fun error(
        format: String?,
        vararg arguments: Any?,
    ) = originalLogger.error(format, *maskAll(arguments))

    override fun error(
        marker: Marker?,
        format: String?,
        vararg arguments: Any?,
    ) = originalLogger.error(marker, format, *maskAll(arguments))

    override fun error(
        format: String?,
        arg: Any?,
    ) = originalLogger.error(format, mask(arg))

    override fun error(
        format: String?,
        arg1: Any?,
        arg2: Any?,
    ) = originalLogger.error(format, mask(arg1), mask(arg2))

    override fun warn(
        format: String?,
        vararg arguments: Any?,
    ) = originalLogger.warn(format, *maskAll(arguments))

    override fun warn(
        marker: Marker?,
        format: String?,
        vararg arguments: Any?,
    ) = originalLogger.warn(marker, format, *maskAll(arguments))

    override fun warn(
        format: String?,
        arg: Any?,
    ) = originalLogger.warn(format, mask(arg))

    override fun warn(
        format: String?,
        arg1: Any?,
        arg2: Any?,
    ) = originalLogger.warn(format, mask(arg1), mask(arg2))

    override fun info(
        format: String?,
        vararg arguments: Any?,
    ) = originalLogger.info(format, *maskAll(arguments))

    override fun info(
        marker: Marker?,
        format: String?,
        vararg arguments: Any?,
    ) = originalLogger.info(marker, format, *maskAll(arguments))

    override fun info(
        format: String?,
        arg: Any?,
    ) = originalLogger.info(format, mask(arg))

    override fun info(
        format: String?,
        arg1: Any?,
        arg2: Any?,
    ) = originalLogger.info(format, mask(arg1), mask(arg2))

    override fun debug(
        format: String?,
        vararg arguments: Any?,
    ) = originalLogger.debug(format, *maskAll(arguments))

    override fun debug(
        marker: Marker?,
        format: String?,
        vararg arguments: Any?,
    ) = originalLogger.debug(marker, format, *maskAll(arguments))

    override fun debug(
        format: String?,
        arg: Any?,
    ) = originalLogger.debug(format, mask(arg))

    override fun debug(
        format: String?,
        arg1: Any?,
        arg2: Any?,
    ) = originalLogger.debug(format, mask(arg1), mask(arg2))

    override fun trace(
        format: String?,
        vararg arguments: Any?,
    ) = originalLogger.trace(format, *maskAll(arguments))

    override fun trace(
        marker: Marker?,
        format: String?,
        vararg arguments: Any?,
    ) = originalLogger.trace(marker, format, *maskAll(arguments))

    override fun trace(
        format: String?,
        arg: Any?,
    ) = originalLogger.trace(format, mask(arg))

    override fun trace(
        format: String?,
        arg1: Any?,
        arg2: Any?,
    ) = originalLogger.trace(format, mask(arg1), mask(arg2))

    private fun maskAll(arguments: Array<out Any?>?): Array<String?> {
        if (arguments == null) return emptyArray()
        val list =
            arguments.singleOrNull()?.run {
                if (this is List<*>) {
                    this.map { mask(it) }
                } else {
                    listOf(mask(this))
                }
            } ?: arguments.map(this::mask)
        return list.toTypedArray()
    }

    private fun mask(arg: Any?): String? {
        return arg?.toString()
    }

    private fun dynamicLevelLog(
        logLevel: LogLevel,
        message: String,
        arguments: Array<out Any?>? = null,
    ) {
        when (logLevel) {
            LogLevel.DEBUG -> originalLogger.debug(message, *maskAll(arguments))
            LogLevel.TRACE -> originalLogger.trace(message, *maskAll(arguments))
            LogLevel.INFO -> originalLogger.info(message, *maskAll(arguments))
            LogLevel.WARN -> originalLogger.warn(message, *maskAll(arguments))
            LogLevel.ERROR -> originalLogger.error(message, *maskAll(arguments))
            else -> {
                // ログ出力無し
            }
        }
    }

    fun msg(
        msgLogId: MsgLogId,
        vararg arguments: Any?,
    ) {
        dynamicLevelLog(msgLogId.logLevel, "[${msgLogId.name}]:${msgLogId.message}", arguments)
    }

    fun loggingException(
        logLevel: LogLevel,
        message: String?,
        ex: Exception? = null,
    ) {
        when (logLevel) {
            LogLevel.DEBUG -> originalLogger.debug(message, ex)
            LogLevel.TRACE -> originalLogger.trace(message, ex)
            LogLevel.INFO -> originalLogger.info(message, ex)
            LogLevel.WARN -> originalLogger.warn(message, ex)
            LogLevel.ERROR -> originalLogger.error(message, ex)
            else -> {
                // ログ出力無し
            }
        }
    }
}
