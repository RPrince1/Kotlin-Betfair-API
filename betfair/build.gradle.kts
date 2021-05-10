
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
}

group = "com.prince"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_15

repositories {
	mavenCentral()
}

object Versions {
	const val jacksonKotlin = "2.11.4"
	const val kotlinLogging = "2.0.6"
	const val kotest = "4.4.3"
	const val mockk = "1.11.0"
	const val okhttp = "4.9.1"
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.apache.httpcomponents:httpclient:4.5.13")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jacksonKotlin}")
	implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")
	implementation("com.squareup.okhttp3:okhttp:${Versions.okhttp}")

	testImplementation("io.kotest:kotest-runner-junit5:${Versions.kotest}")
	testImplementation("io.kotest:kotest-assertions-core:${Versions.kotest}")
	testImplementation("io.mockk:mockk:${Versions.mockk}")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "15"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
