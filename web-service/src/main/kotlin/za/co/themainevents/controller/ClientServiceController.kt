package za.co.themainevents.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import za.co.themainevents.datasource.dto.ListOfClients
import za.co.themainevents.datasource.dto.ListOfEvents
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event
import za.co.themainevents.service.WebService
import java.io.IOException

/**
 * Controller class for handling client-related web API requests.
 */
@RestController
@RequestMapping("/")
class ClientServiceController(private val webService : WebService) {


    @ExceptionHandler(NoSuchElementException :: class)
    fun handleException(e : NoSuchElementException) : ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)


    @ExceptionHandler(IOException :: class)
    fun handleBadException(e : IOException) : ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)


    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerClient(@RequestBody client: Client) = webService.registerClient(client)


    @PostMapping("event")
    @ResponseStatus(HttpStatus.CREATED)
    fun createEvent(@RequestBody event: Event) = webService.createEvent(event)


    @PostMapping("login")
    fun clientLogin(@RequestBody client: Client) = webService.clientLogin(client)


    @GetMapping("friends/{clientID}")
    fun getFriends(@PathVariable clientID : Int): ListOfClients = webService.getFriends(clientID)


    @GetMapping("events/{clientID}")
    fun getClientEvents(@PathVariable clientID: Int): ListOfEvents = webService.getClientEvents(clientID)


    @GetMapping("events")
    fun getOtherClientsEvents() : ListOfEvents = webService.getOtherClientsEvents()


    @GetMapping("clients")
    fun getAllClients() : ListOfClients = webService.getAllClients()
}