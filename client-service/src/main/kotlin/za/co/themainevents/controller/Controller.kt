package za.co.themainevents.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import za.co.themainevents.models.Client

@RestController
@RequestMapping("/client")
class Controller {

    @GetMapping("friends")
    fun getClientFriends() : List<Client>{
        return listOf(
            Client("example1", "123"),
            Client("example2", "123"),
            Client("example3", "123")
        )
    }


    @GetMapping("/register/{email}/{password}")
    fun registerClient(@PathVariable email: String, @PathVariable password: String) : String = "Welcome $email"


    @GetMapping("/login/{email}/{password}")
    fun clientLogin(@PathVariable email: String, @PathVariable password: String) : String = "Welcome $email"
}