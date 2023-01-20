package za.co.themainevents.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


/**
 * The main class for the web service application.
 * This class is annotated with SpringBootApplication and ComponentScan to enable
 * Spring Boot and scan for components in the specified package.
 */
@SpringBootApplication
@ComponentScan(basePackages = ["za.co.themainevents.controller"])
class WebService


/**
 * The main entry point for the web service application.
 * @param args command line arguments passed to the application.
 */
fun main (args: Array<String>){
    runApplication<WebService>(*args)
}
