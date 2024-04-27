package seki.kotlin.backend.base.batch

import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.annotation.MapperScans
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.ExitCodeGenerator
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import seki.kotlin.backend.base.batch.core.BatchContext
import seki.kotlin.backend.base.common.log.MsgLogger
import kotlin.system.exitProcess

/**
 * アプリケーション起動用クラス
 */
@MapperScans(
    MapperScan("seki.kotlin.backend.base.common.db"),
    MapperScan("seki.kotlin.backend.base.batch.**.mapper"),
)
@SpringBootApplication(
    scanBasePackages = [
        "seki.kotlin.backend.base.common",
        "seki.kotlin.backend.base.batch",
    ],
)
@ConfigurationPropertiesScan(
    basePackages = [
        "seki.kotlin.backend.base.common",
        "seki.kotlin.backend.base.batch",
    ],
)
class BatchApplication(
    private val msgLogger: MsgLogger,
    private val batchContext: BatchContext,
) : CommandLineRunner, ExitCodeGenerator {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val context =
                runApplication<BatchApplication>(*args) {
                    webApplicationType = WebApplicationType.NONE
                }
            exitProcess(SpringApplication.exit(context))
        }
    }

    override fun run(vararg args: String?) {
        batchContext.args = args.asList()
        batchContext.validate()
    }

    override fun getExitCode(): Int {
        return if (batchContext.hasError()) {
            msgLogger.error(batchContext.exception?.message, batchContext.exception)
            -1
        } else {
            0
        }
    }
}
