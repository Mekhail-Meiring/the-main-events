package za.co.themainevents.datasource

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName



@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@Testcontainers
internal class ClientDataSourceTest(
    @Autowired val clientDataSource: ClientDataSource,
    @Autowired val jbdc : JdbcTemplate
) {

    @AfterEach
    fun tearDown() {
        jbdc.execute("TRUNCATE TABLE clients CASCADE")
        jbdc.execute("TRUNCATE TABLE events CASCADE")
        jbdc.execute("TRUNCATE TABLE friends CASCADE")
    }

    companion object {

        @Container
        private val postgreSQLContainer = postgres("postgres:15.1") {
            withDatabaseName("client-service-test-db")
            withUsername("user")
            withPassword("password")
            withInitScript("sql/schema.sql")
        }

        @JvmStatic
        @DynamicPropertySource
        fun datasourceConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
        }
    }

    @Test
    fun `should show database is running and show the name of the database` () {

        // when/then
        assertTrue(postgreSQLContainer.isRunning)
        assertEquals("client-service-test-db", postgreSQLContainer.databaseName)

    }


}


/**
 * Creates and starts a PostgreSQL container using the specified Docker image.
 *
 * @param imageName The name of the Docker image to use for the container
 * @param opts Additional configuration options for the container
 */
fun postgres (imageName: String, opts: JdbcDatabaseContainer<Nothing>.() -> Unit) =
    PostgreSQLContainer<Nothing>(DockerImageName.parse(imageName)).apply(opts)
