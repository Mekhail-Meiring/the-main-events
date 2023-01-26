package za.co.themainevents

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import za.co.themainevents.datasource.Datasource
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event
import za.co.themainevents.service.ClientService


internal class ClientServiceTest{

    private val datasource : Datasource = mockk(relaxed = true)

    private val clientService : ClientService = ClientService(datasource)

    @Test
    fun `should call its data source to register a new client` () {
        // given
        val client = Client(
            1, "user", "surname",
            "user@email", "User123"
        )

        // when
        clientService.registerClient(client)

        // then
        verify { datasource.registerClient(client) }

    }

    @Test
    fun `should call its data source to create a new event` () {
        // given
        val event = Event(1, 1, "Cape Town", "1/1/23", "17:00", "example")

        // when
        clientService.createNewEvent(event)

        // then
        verify { datasource.createNewEvent(event) }
    }

    @Test
    fun `should call its data source to log a user in` () {
        // given
        val email = "user@email"
        val password = "123User"

        // when
        clientService.clientLogin(email, password)

        // then
        verify { datasource.clientLogin(email, password) }

    }

    @Test
    fun `should call its datasource to get a clients friends` () {
        // given
        val clientId = 1

        // when
        clientService.getClientFriends(clientId)

        // then
        verify { datasource.getFriends(clientId) }
    }

    @Test
    fun `should call its data source to get all clients` () {

        // when
        clientService.getClients()

        // then
        verify(exactly = 1) { datasource.getClients() }
    }

    @Test
    fun `should call its data source to get all events` () {
        // when
        clientService.getAllEvents()

        // then
        verify(exactly = 1) { datasource.getAllEvents() }
    }

    @Test
    fun `should call its data source to get events for a specific client` () {
        // given
        val clientId = 1

        // when
        clientService.getEventsForClient(clientId)

        // then
        verify { datasource.getClientEvents(clientId) }
    }

    @Test
    fun `should call its data source to update an existing event` () {
        // given
        val newEvent = Event(
            1, 1, "Cape town", "1/1/21", "17:00", "food"
        )

        // when
        clientService.updateEvent(newEvent, 1)

        // then
        verify { datasource.updateEvent(newEvent, 1) }
    }


    @Test
    fun `should call its datasource to update client information` () {
        // given
        val client = Client(1,"user", "lastname", "user@email", "123User")

        // when
        clientService.updateClientInformation(client)

        // then
        verify { datasource.updateClientInformation(client) }
    }

    @Test
    fun `should call its data source to remove an existing event` () {
        // given
        val eventID = 1

        // when
        clientService.removeEvent(eventID, 1)

        // then
        verify { datasource.removeEvent(eventID, 1) }
    }

    @Test
    fun `should call its data source to remove an existing client` () {
        // given
        val clientID = 1

        // when
        clientService.removeClient(clientID)

        // then
        verify { datasource.removeClient(clientID) }
    }

}
