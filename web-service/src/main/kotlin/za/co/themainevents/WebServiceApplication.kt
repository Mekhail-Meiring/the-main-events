package za.co.themainevents

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.client.RestTemplate


/**
 * The main class for the web service application.
 * This class is annotated with SpringBootApplication and ComponentScan to enable
 * Spring Boot and scan for components in the specified package.
 */
@SpringBootApplication
//@ComponentScan(basePackages = ["za.co.themainevents.controller"])
class WebServiceApplication{

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    fun restTemplate(builder: RestTemplateBuilder) : RestTemplate = builder.build()

}


/**
 * The main entry point for the web service application.
 * @param args command line arguments passed to the application.
 */
fun main (args: Array<String>){
    runApplication<WebServiceApplication>(*args)
}
