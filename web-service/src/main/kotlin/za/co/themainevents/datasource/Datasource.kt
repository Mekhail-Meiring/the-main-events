package za.co.themainevents.datasource

import za.co.themainevents.datasource.dto.ListOfClients
import za.co.themainevents.datasource.dto.ListOfEvents
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event

interface Datasource {

    // Create
    /**
     * Register a new user
     * @param client the new client to be registered
     */
    fun registerUser(client: Client)

    /**
     * Create a new event
     * @param event the new event to be created
     */
    fun createNewEvent(event: Event)


    // Read
    /**
     * Retrieve the list of friends for a client
     * @param clientID the id of the client to check for friends
     * @return a list of clients representing the friends of the specified client
     */
    fun getFriends(clientID: Int): ListOfClients

    /**
     * Retrieve all clients
     * @return a list of all clients
     */
    fun getClients() : ListOfClients

    /**
     * Retrieve all events
     * @return a list of all events
     */
    fun getAllEvents(): ListOfEvents


    /**
     * Retrieve events for a client
     * @param clientID the id of the client whose events should be retrieved
     * @return a list of events for the specified client
     */
    fun getClientEvents(clientID: Int): ListOfEvents


    // Update
    /**
     * Update an existing event
     * @param newEvent the updated version of the event
     * @param oldEventID the id of the event to be updated
     */
    fun updateEvent(newEvent: Event, oldEventID: Int)

    /**
     * Update the password for a client
     * @param client the client whose password should be updated
     * @param newPassword the new password for the client
     */
    fun updateClientPassword(client: Client, newPassword: String)


    // Delete
    /**
     * Remove an event from the database
     * @param event the event to be removed
     */
    fun removeEvent(event: Event)

    /**
     * Remove a client from the database
     * @param client the client to be removed
     */
    fun removeClient(client: Client)


    fun clientLogin(client: Client)
}