package za.co.themainevents.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event
import za.co.themainevents.service.ClientService

@RestController
@RequestMapping("/")
class ServiceController (private val clientService: ClientService){


    @PostMapping("register")
    fun registerClient() : String = "Welcome"


    @PostMapping("event")
    fun createEvent() : String = "Welcome"


    @PostMapping("login")
    fun clientLogin() : String = "Welcome"


    @GetMapping("friends/{clientID}")
    fun getClientFriends(@PathVariable clientID: Int) : List<Client>{
        return emptyList()
    }


    @GetMapping("events/{clientID}")
    fun getClientEvents(@PathVariable clientID : Int) : List<Event>{
        return emptyList()
    }


    @GetMapping("clients")
    fun getAllClients() : List<Client> {
        return clientService.getClients()
    }


    @GetMapping("events")
    fun getOtherClientEvents() : List<Event>{
        return emptyList()
    }


}