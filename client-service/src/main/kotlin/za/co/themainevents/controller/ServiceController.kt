package za.co.themainevents.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event
import za.co.themainevents.models.FriendClientsIDs
import za.co.themainevents.service.ClientService

@RestController
@RequestMapping("/")
class ServiceController (private val clientService: ClientService){


    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerClient(@RequestBody client: Client) : Client = clientService.registerClient(client)


    @PostMapping("event")
    @ResponseStatus(HttpStatus.CREATED)
    fun createEvent(@RequestBody event: Event) : Event = clientService.createNewEvent(event)


    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    fun clientLogin(@RequestParam email: String, @RequestParam password: String) : Client = clientService.clientLogin(email, password)


    @PostMapping("add-friend")
    fun addFriend(@RequestParam clientID: Int, @RequestParam friendID: Int) : FriendClientsIDs = clientService.addFriend(clientID, friendID)


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