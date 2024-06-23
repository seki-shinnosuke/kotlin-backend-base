import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.util.Locale

plugins {
    application
    idea
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.flyway)
    jacoco
}

allprojects {
    group = "seki.kotlin.backend"
    apply {
        plugin("jacoco")
    }

    repositories {
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }

    tasks {
        withType<KotlinCompile>().configureEach {
            dependsOn("ktlintFormat")
            kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict", "-Xemit-jvm-type-annotations", "-java-parameters")
            kotlinOptions.jvmTarget = rootProject.libs.versions.java.get()
            kotlinDaemonJvmArguments.set(listOf("-Xmx4g"))
        }

        withType<Test>().configureEach {
            useJUnitPlatform()
            jvmArgs = listOf("-XX:+EnableDynamicAgentLoading")
            maxHeapSize = "3g"
            val javaToolchains = project.extensions.getByType<JavaToolchainService>()
            javaLauncher.set(
                javaToolchains.launcherFor {
                    languageVersion.set(JavaLanguageVersion.of(rootProject.libs.versions.java.get().toInt()))
                },
            )
            finalizedBy(jacocoTestReport)
        }

        withType<JacocoReport> {
            dependsOn(test)
            reports {
                xml.required = true
            }
            afterEvaluate {
                classDirectories.setFrom(
                    classDirectories.files.map {
                        fileTree(it).matching {
                            exclude(
                                "**/config/**",
                                "**/common/component/**",
                                "**/db/**",
                                "**/enumeration/**",
                                "**/exception/**",
                                "**/extension/**",
                                "**/filter/**",
                                "**/handler/**",
                                "**/log/**",
                                "**/model/**",
                            )
                        }
                    },
                )
            }
        }

        withType<BootJar>().configureEach {
            if (this.project == rootProject || this.project.name == "common") {
                enabled = false
            } else {
                mainClass.set(
                    "${rootProject.group}.${this.project.name}.${this.project.name.replaceFirstChar {
                        if (it.isLowerCase()) {
                            it.titlecase(
                                Locale.getDefault(),
                            )
                        } else {
                            it.toString()
                        }
                    }}Application",
                )
            }
        }

        withType<Jar>().configureEach {
            if (this.project == rootProject) {
                enabled = false
            } else {
                enabled = true
                archiveBaseName.set("${rootProject.name}-${this.project.name}")
            }
        }
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin(rootProject.libs.plugins.spring.boot.get().pluginId)
        plugin(rootProject.libs.plugins.spring.dependency.management.get().pluginId)
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
    }
    dependencies {
        implementation(rootProject.libs.kotlin.reflect)
        implementation(rootProject.libs.kotlin.stdlib)
        implementation(rootProject.libs.kotlin.gradle)
        implementation(rootProject.libs.spring.boot.gradle)
        implementation(rootProject.libs.spring.boot.starter.aop)
        implementation(rootProject.libs.spring.boot.starter.validation)
        implementation(rootProject.libs.spring.boot.starter.actuator)
        implementation(rootProject.libs.apache.commons.lang3)
        implementation(rootProject.libs.apache.commons.text)
        implementation(rootProject.libs.commons.io)

        implementation(rootProject.libs.jackson.module.kotlin)
        implementation(rootProject.libs.mybatis.spring)
        implementation(rootProject.libs.mybatis.dynamic.sql)

        runtimeOnly(rootProject.libs.mysql.connector)
        runtimeOnly(rootProject.libs.logstash.logback)

        testImplementation(rootProject.libs.spring.boot.starter.test) {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
        testImplementation(rootProject.libs.dbunit)
        testImplementation(rootProject.libs.spring.test.dbunit)
        testImplementation(rootProject.libs.mockito)
        testImplementation(rootProject.libs.mockito.kotlin)
    }

    configure<DependencyManagementExtension> {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        filter {
            exclude("**/common/db/**")
        }
    }
}

project(":common") {
    dependencies {
        implementation(rootProject.libs.spring.web)
        implementation(platform(rootProject.libs.awssdk.bom))
        implementation(rootProject.libs.awssdk.s3)
        implementation(rootProject.libs.awssdk.apache.client)
        implementation(rootProject.libs.awssdk.cloudfront)
        implementation(rootProject.libs.awssdk.sts)
        implementation(rootProject.libs.jackson.databind)
        implementation(rootProject.libs.jackson.datatype)
    }

    tasks.bootJar {
        enabled = false
    }
    tasks.bootRun {
        enabled = false
    }
    tasks.jar {
        enabled = true
    }

    val testCompile by configurations.creating
    configurations.create("testArtifacts") {
        extendsFrom(testCompile)
    }
    tasks.register("testJar", Jar::class.java) {
        archiveClassifier.set("test")
        from(sourceSets["test"].output)
    }
    artifacts {
        add("testArtifacts", tasks.named<Jar>("testJar"))
    }
}

project(":api") {
    apply {
        plugin("idea")
    }

    dependencies {
        implementation(project(":common"))
        implementation(rootProject.libs.spring.boot.starter.web)
        implementation(rootProject.libs.spring.boot.starter.security)
        implementation(rootProject.libs.spring.boot.starter.data.redis)
        implementation(rootProject.libs.spring.session.data.redis)

        testImplementation(project(":common", "testArtifacts"))
        testImplementation(rootProject.libs.spring.security.test)
    }

    idea {
        module {
            inheritOutputDirs = false
            outputDir = file("${project.layout.buildDirectory}/classes/kotlin/main")
        }
    }

    springBoot {
        buildInfo()
    }
}

project(":batch") {
    apply {
        plugin("idea")
    }

    dependencies {

        implementation(project(":common"))
        implementation(rootProject.libs.spring.boot.starter.web)

        testImplementation(project(":common", "testArtifacts"))
    }

    idea {
        module {
            inheritOutputDirs = false
            outputDir = file("${project.layout.buildDirectory}/classes/kotlin/main")
        }
    }

    springBoot {
        buildInfo()
    }
}

/**
 * MyBatisGenerator設定
 * 「./gradlew mybatisGenerator」コマンドでジェネレート(ディレクトリを先に手動作成する必要あり)
 */
val mybatisGenerator: Configuration by configurations.creating
dependencies {
    runtimeOnly(libs.mysql.connector)

    mybatisGenerator(libs.mybatis.generator)
    mybatisGenerator(libs.mysql.connector)
}
task("mybatisGenerator") {
    doLast {
        ant.withGroovyBuilder {
            "taskdef"(
                "name" to "mbgenerator",
                "classname" to "org.mybatis.generator.ant.GeneratorAntTask",
                "classpath" to mybatisGenerator.asPath,
            )
        }
        ant.withGroovyBuilder {
            "mbgenerator"("overwrite" to true, "configfile" to "$rootDir/db/mybatis/generatorConfig.xml", "verbose" to true)
        }
    }
}

buildscript {
    dependencies {
        classpath(libs.flyway.mysql)
    }
}
flyway {
    configFiles = arrayOf("db/flyway/base.conf")

    tasks.named("flywayBaseline") { mustRunAfter("flywayClean") }
    tasks.named("flywayMigrate") { mustRunAfter("flywayBaseline") }

    tasks.register("flywayInit") {
        group = "flyway"
        dependsOn(tasks.named("flywayClean"))
        dependsOn(tasks.named("flywayBaseline"))
        dependsOn(tasks.named("flywayMigrate"))
    }
}
