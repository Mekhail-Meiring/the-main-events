package za.co.themainevents.datasource

import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import za.co.themainevents.datasource.dto.ClientDO
import za.co.themainevents.datasource.dto.EventDO
import za.co.themainevents.datasource.dto.FriendsClientIDsDO
import za.co.themainevents.exceptions.*
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event


/**
 * The data source responsible for retrieving, inserting and updating data in the Postgres Database
 * @author Mekhail Meiring
 * @since 26/01/2023
 */
@Repository("clientDatasource")
class ClientDataSource : Datasource{

    /**
     * Initializes the datasource by connecting to the postgresql database using HikariDataSource
     * and adding a shutdown hook to close the connection pool on shutdown
     */
    init {
        val dataSource = HikariDataSource().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/the_main_events"
            username = "postgres"
            password = "postgres"
        }

        // Close connection pool on shutdown
        Runtime.getRuntime().addShutdownHook(Thread {
            dataSource.close()
        })

        Database.connect(dataSource)
    }


    /**
     * Returns a list of clients from the database
     * @return List<Client> list of clients
     */
    override fun getClients() : List<Client> {
        val listOfClients = transaction {
             ClientDO.selectAll().map {
                Client(
                    it[ClientDO.clientId], it[ClientDO.firstName],
                    it[ClientDO.lastName], it[ClientDO.email],
                    it[ClientDO.password]
                )
            }
        }

        return listOfClients
    }


    /**
     * Retrieves a list of all events from the database.
     *
     * @return a list of [Event] objects representing all events in the database
     */
    override fun getAllEvents(): List<Event> {

        val listOfEvents = transaction {
            EventDO.selectAll().map {
                Event(
                    it[EventDO.eventId], it[EventDO.fromClientId], it[EventDO.location],
                    it[EventDO.date], it[EventDO.time], it[EventDO.description]
                )
            }
        }

        return listOfEvents
    }


    /**
     * Returns a list of events for a specific client
     *
     * @param clientID The ID of the client
     * @return A list of events for the specified client
     */
    override fun getClientEvents(clientID: Int): List<Event> {

        val listOfEvents = transaction {
            EventDO.select(EventDO.fromClientId eq clientID).map {
                Event(
                    it[EventDO.eventId], it[EventDO.fromClientId], it[EventDO.location],
                    it[EventDO.date], it[EventDO.time], it[EventDO.description]
                )
            }
        }
        return listOfEvents
    }


    /**
     * Retrieves the list of friends for a given client by their client ID.
     *
     * @param clientID The ID of the client whose friends are to be retrieved.
     * @return A list of clients that are friends of the given client.
     */
    override fun getFriends(clientID: Int): List<Client> {

        val listOfClientsFriendClientsIDs = transaction {
            (ClientDO innerJoin FriendsClientIDsDO).select {
                FriendsClientIDsDO.clientId eq clientID and (FriendsClientIDsDO.friendId eq ClientDO.clientId)
            }.map {
                Client(
                    it[ClientDO.clientId], it[ClientDO.firstName],
                    it[ClientDO.lastName], it[ClientDO.email],
                    it[ClientDO.password]
                )
            }
        }

        return listOfClientsFriendClientsIDs
    }


    /**
     * Update an existing event
     * @param newEvent: the updated event object
     * @param clientId: the ID of the client who created the event
     * @return Event: the updated event object
     */
    override fun updateEvent(newEvent: Event, clientId: Int): Event {

        val rowsAffected = transaction {
            EventDO.update({ EventDO.eventId eq newEvent.eventId and (EventDO.fromClientId eq clientId)}) {
                it[fromClientId] = newEvent.fromClientId
                it[location] = newEvent.location
                it[date] = newEvent.date
                it[time] = newEvent.time
                it[description] = newEvent.description
            }
        }

        if (rowsAffected == 0) {
            throw EventNotFoundException("Event with id ${newEvent.eventId} not found")
        }

        return newEvent
    }


    /**
     * Update the information of a client.
     *
     * @param client the updated information of the client.
     * @return the updated client.
     * @throws ClientNotFoundException if the client is not found.
     */
    override fun updateClientInformation(client: Client): Client {

        val rowsAffected = transaction {
            ClientDO.update({ ClientDO.clientId eq client.clientId }) {
                it[firstName] = client.firstName
                it[lastName] = client.lastName
                it[email] = client.email
                it[password] = client.password
            }
        }

        if (rowsAffected == 0) {
            throw ClientNotFoundException("Client with id ${client.clientId} not found")
        }

        return client
    }


    /**
     * Removes an event from the database.
     * @param eventId: The id of the event to be removed.
     * @param clientID: The id of the client who created the event.
     * @throws EventNotFoundException if the event with the given id is not found.
     */
    override fun removeEvent(eventId: Int, clientID: Int) {

        val rowsAffected = transaction {
            EventDO.deleteWhere { this.eventId eq eventId and (fromClientId eq clientID)}
        }

        if (rowsAffected == 0) {
            throw EventNotFoundException("Event with id $eventId not found")
        }

    }


    /**
     * Removes a client with the given id from the database.
     *
     * @param clientId The id of the client to remove.
     * @throws ClientNotFoundException if the client with the given id is not found in the database.
     */
    override fun removeClient(clientId: Int) {

        val rowsAffected = transaction {
            ClientDO.deleteWhere { ClientDO.clientId eq clientId }
        }

        if (rowsAffected == 0) {
            throw ClientNotFoundException("Client with id $clientId not found")
        }
    }


    override fun addFriend(clientID: Int, friendID: Int) {
        transaction {
            FriendsClientIDsDO.insert {
                it[clientId] = clientID
                it[friendId] = friendID
            }
        }
    }


    /**
     * Registers a new client to the database
     * @param client: The client to register
     * @throws ClientAlreadyExistsException: When a client with the same email already exists in the database.
     * @return The registered client.
     */
    override fun registerClient(client: Client): Client {

        transaction {

            if (ClientDO.select { ClientDO.email eq client.email }.firstOrNull() != null) {
                throw ClientAlreadyExistsException("Client with email ${client.email} already exists")
            }

            ClientDO.insert {
                it[firstName] = client.firstName
                it[lastName] = client.lastName
                it[email] = client.email
                it[password] = client.password
            }
        }

        return client
    }


    /**
     * Creates a new event.
     * @param event: The event to create
     * @throws DuplicateEventException: When an event with the same details already exists in the database.
     * @return The created event
     */
    override fun createNewEvent(event: Event): Event {
        transaction {
            val alreadyExists = EventDO.select {
                (EventDO.fromClientId eq event.fromClientId) and (EventDO.date eq event.date) and (EventDO.time eq event.time) and (EventDO.location eq event.location) and (EventDO.description eq event.description)
            }.firstOrNull() != null

            if (alreadyExists) {
                throw DuplicateEventException("Event with id ${event.eventId} already exists")
            }

            EventDO.insert {
                it[fromClientId] = event.fromClientId
                it[location] = event.location
                it[date] = event.date
                it[time] = event.time
                it[description] = event.description
            }
        }

        return event
    }


    /**
     * Attempts to log a client in with the provided email and password.
     * @param email: The email of the client trying to log in.
     * @param password: The password of the client trying to log in.
     * @return: A client object representing the client that has successfully logged in.
     * @throws InvalidClientCredentialsException: If the provided email and password do not match any existing client.
     */
    override fun clientLogin(email: String, password: String): Client {

        val client = transaction {

            ClientDO.select { ClientDO.email eq email and (ClientDO.password eq password) }.firstOrNull()
                ?.let {
                    Client(
                        it[ClientDO.clientId], it[ClientDO.firstName],
                        it[ClientDO.lastName], it[ClientDO.email],
                        it[ClientDO.password]
                    )
                } ?: throw InvalidClientCredentialsException("Invalid email or password")
        }

        return client
    }
}