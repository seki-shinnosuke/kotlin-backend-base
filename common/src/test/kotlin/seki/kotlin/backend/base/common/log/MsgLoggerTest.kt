package seki.kotlin.backend.base.common.log

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.LoggingEvent
import ch.qos.logback.core.Appender
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import seki.kotlin.backend.base.common.BaseCommonTestBase

internal class MsgLoggerTest : BaseCommonTestBase() {
    @Autowired
    private lateinit var logTest: LogTest

    @Captor
    private lateinit var captorLoggingEvent: ArgumentCaptor<LoggingEvent>

    @Mock
    private lateinit var mockAppender: Appender<ILoggingEvent>

    @DisplayName("MsgLogger：正常：ログ出力")
    @Test
    fun testLogPrint() {
        val logger = LoggerFactory.getLogger(LogTest::class.java) as Logger
        logger.addAppender(mockAppender)
        logger.level = Level.DEBUG
        logTest.logPrint()
        // ログ検証
        verify(mockAppender, times(4)).doAppend(captorLoggingEvent.capture())
        val logEvents = captorLoggingEvent.allValues
        Assertions.assertEquals(logEvents[0].level, Level.INFO)
        Assertions.assertEquals(logEvents[0].formattedMessage, "INFO TEST")
        Assertions.assertEquals(logEvents[1].level, Level.WARN)
        Assertions.assertEquals(logEvents[1].formattedMessage, "WARN TEST")
        Assertions.assertEquals(logEvents[2].level, Level.ERROR)
        Assertions.assertEquals(logEvents[2].formattedMessage, "ERROR TEST")
        Assertions.assertEquals(logEvents[3].level, Level.DEBUG)
        Assertions.assertEquals(logEvents[3].formattedMessage, "DEBUG TEST")
    }

    @DisplayName("MsgLogger：正常：メッセージ出力")
    @Test
    fun testMsgPrint() {
        val logger = LoggerFactory.getLogger(LogTest::class.java) as Logger
        logger.addAppender(mockAppender)
        logTest.msgPrint()
        // ログ検証
        verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture())
        val logEvents = captorLoggingEvent.allValues
        Assertions.assertEquals(logEvents[0].level, Level.WARN)
        Assertions.assertEquals(logEvents[0].formattedMessage, "[API_LOG_00001]:Not Login.")
        Assertions.assertEquals(logEvents[1].level, Level.WARN)
        Assertions.assertEquals(logEvents[1].formattedMessage, "[API_LOG_00002]:Bad parameter. info")
    }
}

@Component
internal class LogTest(
    private val msgLogger: MsgLogger,
) {
    fun logPrint() {
        msgLogger.info("INFO TEST")
        msgLogger.warn("WARN TEST")
        msgLogger.error("ERROR TEST")
        msgLogger.debug("DEBUG TEST")
    }

    fun msgPrint() {
        msgLogger.msg(MsgLogId.API_LOG_00001)
        msgLogger.msg(MsgLogId.API_LOG_00002, "info")
    }
}
