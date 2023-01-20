package za.co.themainevents
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Service
import za.co.themainevents.datasource.ClientDataSource
import za.co.themainevents.models.Client


@SpringBootApplication
@ComponentScan(basePackages = ["za.co.themainevents.controller"])
class ClientService

fun main (args: Array<String>) {
    runApplication<ClientService>(*args)
}