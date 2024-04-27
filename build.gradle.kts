import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.util.Locale

object Versions {
    const val JDK = "21"
}

plugins {
    application
    idea
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.jlleitschuh.gradle.ktlint") version "12.0.3"
    id("org.flywaydb.flyway") version "10.4.1"
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
            kotlinOptions.jvmTarget = Versions.JDK
            kotlinDaemonJvmArguments.set(listOf("-Xmx4g"))
        }

        withType<Test>().configureEach {
            useJUnitPlatform()
            jvmArgs = listOf("-XX:+EnableDynamicAgentLoading")
            maxHeapSize = "3g"
            val javaToolchains = project.extensions.getByType<JavaToolchainService>()
            javaLauncher.set(
                javaToolchains.launcherFor {
                    languageVersion.set(JavaLanguageVersion.of(Versions.JDK.toInt()))
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
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jlleitschuh.gradle.ktlint")
    }
    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
        implementation("org.springframework.boot:spring-boot-gradle-plugin:3.2.3")
        implementation("org.springframework.boot:spring-boot-starter-aop")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.apache.commons:commons-lang3:3.14.0")
        implementation("org.apache.commons:commons-text:1.11.0")
        implementation("commons-io:commons-io:2.15.1")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
        implementation("org.mybatis.dynamic-sql:mybatis-dynamic-sql:1.5.0")

        runtimeOnly("com.mysql:mysql-connector-j")
        runtimeOnly("net.logstash.logback:logstash-logback-encoder:7.3")

        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
        testImplementation("org.dbunit:dbunit:2.7.2")
        testImplementation("com.github.springtestdbunit:spring-test-dbunit:1.3.0")
        testImplementation("org.mockito:mockito-inline:5.2.0")
        testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
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
        implementation("org.springframework:spring-web")
        implementation(platform("software.amazon.awssdk:bom:2.21.42"))
        implementation("software.amazon.awssdk:s3")
        implementation("software.amazon.awssdk:apache-client")
        implementation("software.amazon.awssdk:cloudfront")
        implementation("software.amazon.awssdk:sts")
        implementation("com.fasterxml.jackson.core:jackson-databind")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
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
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("org.springframework.session:spring-session-data-redis")

        testImplementation(project(":common", "testArtifacts"))
        testImplementation("org.springframework.security:spring-security-test")
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
        implementation("org.springframework.boot:spring-boot-starter-web")

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
    runtimeOnly("com.mysql:mysql-connector-j")

    mybatisGenerator("org.mybatis.generator:mybatis-generator-core:1.4.2")
    mybatisGenerator("com.mysql:mysql-connector-j:8.2.0")
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
        classpath("org.flywaydb:flyway-mysql:10.4.1")
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
