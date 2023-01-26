package za.co.themainevents.datasource.mock

import org.springframework.stereotype.Repository
import za.co.themainevents.datasource.Datasource
import za.co.themainevents.exceptions.*
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event
import za.co.themainevents.models.FriendClientsIDs


@Repository
class MockClientDataSource : Datasource{

    private val listOfClients = arrayListOf(
        Client(1, "John", "Doe", "example@email", "123"),
        Client(2, "John", "Doe",  "example1@email", "123"),
        Client(3, "John", "Doe",  "example2@email", "123")
    )

    private val listOfFriendClientsIDS = arrayListOf(
        FriendClientsIDs(1,2),
        FriendClientsIDs(2, 1),
        FriendClientsIDs(1, 3),
        FriendClientsIDs(3, 1)
    )

    private val listOfEvents = arrayListOf(
        Event(1,1,"Cape Town","01/01/2023","11:00","food"),
        Event(2,2,"Cape Town","02/01/2023","12:00","gatsby"),
        Event(3,3,"Cape Town","03/01/2023","13:00","")
    )


    override fun registerClient(client: Client): Client {

        if (client.email.isBlank() || client.firstName.isBlank() || client.lastName.isBlank() || client.password.isBlank()){
            throw InvalidClientCredentialsException("Client must contain a first name, last name, an email and a password")
        }

        listOfClients.forEach {
            if (it.email == client.email) {
                throw ClientAlreadyExistsException("There already contains a user with email: ${client.email}")
            }

            if (it.clientId == client.clientId) {
                throw DuplicateClientIdException("Client id must be unique")
            }
        }

        listOfClients.add(client)
        return client
    }


    /**
     * Create a new event
     * @param event the new event to be created
     */
    override fun createNewEvent(event: Event): Event {

        if (event.date.isBlank() || event.time.isBlank()) {
            throw InvalidEventDateAndTimeException("Event must contain a date and a time")
        }

        listOfEvents.forEach{
            if(it.eventId == event.eventId){
                throw DuplicateEventException("Event ID must be unique")
            }
        }

        listOfEvents.add(event)
        return event
    }


    override fun clientLogin(email: String, password: String): Client {

        val client : Client? = listOfClients.firstOrNull { it.email == email && it.password == password }

        return client
            ?: throw InvalidClientCredentialsException("Client email or password are incorrect")

    }


    override fun getFriends(clientID: Int): List<Client> {

        if (!listOfClients.any { it.clientId == clientID }) {
            throw ClientNotFoundException("Client not found in database.")
        }

        val friendsClientIDs : List<FriendClientsIDs> = listOfFriendClientsIDS.filter{it.clientId == clientID}

        val friends = arrayListOf<Client>()

        friendsClientIDs.forEach { friendId ->
            val friend = listOfClients.find { it.clientId == friendId.friendId }
            friend?.let { friends.add(it) }
        }

        return friends
    }


    override fun getClients(): List<Client> {
        return listOfClients
    }


    override fun getAllEvents(): List<Event> {
        return listOfEvents
    }


    override fun getClientEvents(clientID: Int): List<Event> {
        return listOfEvents.filter { it.fromClientId == clientID }
    }


    override fun updateEvent(newEvent: Event, clientId: Int): Event {

        if (!listOfEvents.removeIf { it.eventId == newEvent.eventId }) {
            throw FailedToUpdateEventInformationException("Unable to update event information, because it does not exist")
        }

        listOfEvents.add(newEvent)
        return newEvent

    }


    override fun updateClientInformation(client: Client): Client {

        if (!listOfClients.removeIf { it.clientId == client.clientId }) {
            throw FailedToUpdateClientInformationException("Client does not exist")
        }

        listOfClients.add(client)
        return client
    }



    override fun removeEvent(eventId: Int, clientID: Int) {
        if (!listOfEvents.removeIf { it.eventId == eventId}) {
            throw EventNotFoundException("Unable to remove the event, because it does not exist")
        }
    }


    override fun removeClient(clientId: Int) {
        if (!listOfClients.removeIf { it.clientId == clientId }) {
            throw ClientNotFoundException("Client does not exist")
        }
    }

    override fun addFriend(clientID: Int, friendID: Int) {
        TODO("Not yet implemented")
    }


}