package za.co.themainevents.datasource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import za.co.themainevents.datasource.dto.ListOfClients
import za.co.themainevents.datasource.dto.ListOfEvents
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event
import java.io.IOException


@Repository("network")
class NetworkDatasource
    (@Autowired private val restTemplate: RestTemplate) : Datasource {

    val clientServiceAddress = "http://localhost:8000"

    /**
     * Register a new user
     * @param client the new client to be registered
     */
    override fun registerUser(client: Client) {
        val response = restTemplate.postForEntity("$clientServiceAddress/register", client, Client::class.java)
    }

    /**
     * Create a new event
     * @param event the new event to be created
     */
    override fun createNewEvent(event: Event) {
        val response = restTemplate.postForEntity("$clientServiceAddress/event", event, Event::class.java)
    }

    /**
     * Retrieve the list of friends for a client
     * @param clientID the id of the client to check for friends
     * @return a list of clients representing the friends of the specified client
     */
    override fun getFriends(clientID: Int): ListOfClients {
        val response = restTemplate.getForEntity<ListOfClients>("$clientServiceAddress/friends/$clientID")
        return response.body
            ?: throw IOException("Could not fetch friends of client with id: $clientID")
    }


    /**
     * Retrieve all clients
     * @return a list of all clients
     */
    override fun getClients(): ListOfClients {
        val response = restTemplate.getForEntity<ListOfClients>("$clientServiceAddress/clients")
        return response.body
            ?: throw IOException("Could not fetch clients")
    }

    /**
     * Retrieve all events
     * @return a list of all events
     */
    override fun getAllEvents(): ListOfEvents {
        val response = restTemplate.getForEntity<ListOfEvents>("$clientServiceAddress/events")
        return response.body
            ?: throw IOException("Could not fetch all events")
    }

    /**
     * Retrieve events for a client
     * @param clientID the id of the client whose events should be retrieved
     * @return a list of events for the specified client
     */
    override fun getClientEvents(clientID: Int): ListOfEvents {
        val response = restTemplate.getForEntity<ListOfEvents>("$clientServiceAddress/events/$clientID")
        return response.body
            ?: throw IOException("Could not fetch events for client of id: $clientID")
    }

    /**
     * Update an existing event
     * @param newEvent the updated version of the event
     * @param oldEventID the id of the event to be updated
     */
    override fun updateEvent(newEvent: Event, oldEventID: Int) {
        TODO("Not yet implemented")
    }

    /**
     * Update the password for a client
     * @param client the client whose password should be updated
     * @param newPassword the new password for the client
     */
    override fun updateClientPassword(client: Client, newPassword: String) {
        TODO("Not yet implemented")
    }

    /**
     * Remove an event from the database
     * @param event the event to be removed
     */
    override fun removeEvent(event: Event) {
        TODO("Not yet implemented")
    }

    /**
     * Remove a client from the database
     * @param client the client to be removed
     */
    override fun removeClient(client: Client) {
        TODO("Not yet implemented")
    }


    override fun clientLogin(client: Client) {
        val response = restTemplate.postForEntity("$clientServiceAddress/login", client, Client::class.java)
    }
}