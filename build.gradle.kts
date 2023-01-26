import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

subprojects {
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}


allprojects{
	group = "za.co.themainevents"
	version = "0.0.1-SNAPSHOT"
	java.sourceCompatibility = JavaVersion.VERSION_17

	repositories {
		mavenCentral()
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
	}

	dependencies {
		implementation(kotlin("stdlib-jdk8"))
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		implementation("com.squareup.retrofit2:retrofit:2.9.0")
		implementation("com.squareup.retrofit2:converter-gson:2.1.0")
		implementation("com.zaxxer:HikariCP:3.4.5")
		implementation("org.postgresql:postgresql:42.3.1")
		implementation("org.jetbrains.exposed:exposed-core:0.40.1")
		implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
		implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("io.mockk:mockk:1.10.4")

	}
}

plugins {
	id("org.springframework.boot") version "3.0.1-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.21"
	kotlin("plugin.spring") version "1.7.21"
}