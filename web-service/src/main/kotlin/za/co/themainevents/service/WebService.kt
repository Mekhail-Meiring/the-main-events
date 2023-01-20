package za.co.themainevents.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import za.co.themainevents.controller.ClientController

@SpringBootApplication
@ComponentScan(basePackages = ["za.co.themainevents.controller"])
class WebService

fun main (args: Array<String>){
    runApplication<WebService>(*args)
}
