
extra["testcontainersVersion"] = "1.17.6"

dependencies{

    val postgresVersion = "42.3.1"
    val jetbrainsExposedVersion = "0.40.1"

    // Common module for project libraries:
    implementation(project(":common"))

    // Hikari:
    implementation("com.zaxxer:HikariCP:3.4.5")

    // Postgresql dbc driver:
    runtimeOnly("org.postgresql:postgresql:$postgresVersion")

    // Jetbrains Exposed:
    implementation("org.jetbrains.exposed:exposed-core:$jetbrainsExposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$jetbrainsExposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$jetbrainsExposedVersion")

    // Spring:
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    // TestContainers:
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")

}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}