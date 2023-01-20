package za.co.themainevents.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import za.co.themainevents.api.ApiHandler
import za.co.themainevents.api.ClientServiceApiInterface
import za.co.themainevents.models.Client


@RestController
@RequestMapping("/webApi/clients")
class ClientController {

    val clientService = ApiHandler().createClientServiceApi("http://localhost:8000")

    @GetMapping
    fun getFriends(): List<Client>? {
        val data =  clientService.getFriendsList()
        return data.execute().body()
    }


    @GetMapping("/register/{email}/{password}")
    fun registerClient(@PathVariable email: String, @PathVariable password: String) : String {
        return "Welcome $email"
    }


    @GetMapping("/login/{email}/{password}")
    fun clientLogin(@PathVariable email: String, @PathVariable password: String) : String = "Welcome $email"
}