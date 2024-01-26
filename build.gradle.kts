import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

allprojects {
    group = "com.example"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
        maven("https://repo.spring.io/snapshot")
    }
}

subprojects {
    apply {
        plugin("java")
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.kapt")
        plugin("kotlin")
        plugin("kotlin-spring")
    }

    dependencies {

        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

        testImplementation("org.springframework.boot:spring-boot-starter-test")

        testImplementation("io.projectreactor:reactor-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "21"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.getByName("bootJar") {
        enabled = false
    }

    tasks.getByName("jar") {
        enabled = true
    }
}
