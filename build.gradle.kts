import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.7.21"
	kotlin("plugin.spring") version "1.7.21"
	id("org.springframework.boot") version "3.0.1-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.0"
}

subprojects {
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")

	dependencies {

		// Kotlin:
		implementation(kotlin("stdlib-jdk8"))
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

		// Spring:
		implementation("org.springframework.boot:spring-boot-starter-web")
		testImplementation("org.springframework.boot:spring-boot-starter-test")

		// Mockk:
		testImplementation("io.mockk:mockk:1.10.4")

		// Will take advantage of Kotlin's syntax to give us some assertion
		testImplementation("io.kotest:kotest-assertions-core-jvm:4.3.1")
	}

	tasks {
		withType<KotlinCompile> {
			kotlinOptions {
				freeCompilerArgs = listOf("-Xjsr305=strict")
				jvmTarget = "17"
			}
		}
		withType<Test> {
			useJUnitPlatform()
		}
	}
}

allprojects {
	group = "za.co.themainevents"
	version = "0.0.1-SNAPSHOT"
	java.sourceCompatibility = JavaVersion.VERSION_17

	repositories {
		mavenCentral()
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
	}
}

tasks{
	withType<Jar>{
		enabled = true
	}
	bootJar {
		enabled = false
	}

	bootBuildImage{
		enabled = false
	}
}
