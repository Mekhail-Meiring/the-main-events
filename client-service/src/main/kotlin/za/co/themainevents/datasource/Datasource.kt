package za.co.themainevents.datasource

import za.co.themainevents.models.Client
import za.co.themainevents.models.Event


/**
 * Data access interface for client datasource.
 */
interface Datasource {

    // Create
    /**
     * Register a new user
     * @param client the new client to be registered
     * @return Client the newly registered and created client
     */
    fun registerClient(client: Client) : Client

    /**
     * Create a new event
     * @param event the new event to be created
     * @return event the created event
     */
    fun createNewEvent(event: Event) : Event


    fun clientLogin(email: String, password: String) : Client


    // Read
    /**
     * Retrieve the list of friends for a client
     * @param clientID the ID of the client to check for friends
     * @return a list of clients representing the friends of the specified client
     */
    fun getFriends(clientID:Int): List<Client>

    /**
     * Retrieve all clients
     * @return a list of all clients
     */
    fun getClients() : List<Client>

    /**
     * Retrieve all events
     * @return a list of all events
     */
    fun getAllEvents(): List<Event>

    /**
     * Retrieve events for a client
     * @param clientID the id of the client whose events should be retrieved
     * @return a list of events for the specified client
     */
    fun getClientEvents(clientID: Int): List<Event>


    // Update
    /**
     * Update an existing event
     * @param newEvent the updated version of an existing event
     * @param clientId the id of whom the event belongs to
     */
    fun updateEvent(newEvent: Event, clientId: Int) : Event

    /**
     * Update the password for a client
     * @param client the client whose password should be updated
     * @return the client with updated information
     */
    fun updateClientInformation(client: Client) : Client


    // Delete
    /**
     * Remove an event from the database
     * @param eventId the ID of the event to be removed
     * @param clientID the ID of whom the event belongs to
     */
    fun removeEvent(eventId: Int, clientID: Int)

    /**
     * Remove a client from the database
     * @param clientId the ID of the client to be removed
     */
    fun removeClient(clientId: Int)

    fun addFriend(clientID: Int, friendID: Int)
}