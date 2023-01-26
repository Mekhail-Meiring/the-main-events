package za.co.themainevents
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


/**
 * The main class for the client service application.
 * This class is annotated with SpringBootApplication and ComponentScan to enable
 * Spring Boot and scan for components in the specified package.
 */
@SpringBootApplication
//@ComponentScan(basePackages = ["za.co.themainevents.controller"])
class ClientServiceApplication


/**
 * The main entry point for the client service application.
 * @param args command line arguments passed to the application.
 */
fun main (args: Array<String>) {
    runApplication<ClientServiceApplication>(*args)
}