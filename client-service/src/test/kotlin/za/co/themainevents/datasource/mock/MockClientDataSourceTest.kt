package za.co.themainevents.datasource.mock

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import za.co.themainevents.exceptions.ClientAlreadyExistsException
import za.co.themainevents.exceptions.DuplicateClientIdException
import za.co.themainevents.exceptions.InvalidClientCredentialsException
import za.co.themainevents.models.Client


internal class MockClientDataSourceTest {

    private val mockDataSource : MockClientDataSource = MockClientDataSource()

    @Test
    fun `should provide a collection of clients` () {
        // when
        val collectionOfClients = mockDataSource.getClients()

        // then
        assertThat(collectionOfClients.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `collection of clients should provide some mock data` () {
        // when
        val collectionOfClients = mockDataSource.getClients()

        // then
        assertThat(collectionOfClients).allMatch{ it.email.isNotBlank()}

    }

    @Test
    fun `should provide a collection of events` () {

        // when
        val events = mockDataSource.getAllEvents()

        // then
        assertThat(events.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `collection of events should provide mock data` () {
        // when
        val events = mockDataSource.getAllEvents()
        val eventIDs = listOf(1,2,3)

        // then
        assertThat(events).allMatch{eventIDs.contains(it.eventId)}
        assertThat(events).allMatch{it.date.isNotBlank()}
        assertThat(events).allMatch{it.time.isNotBlank()}
        assertThat(events).allMatch{it.location.isNotBlank()}
        assertThat(events).anyMatch{it.description.isNotBlank()}

    }

    @Test
    fun `should be able to register a client and add the client to a database` () {

        // given
        val newClient = Client(
            10, "m", "m", "NeWexample@email", "123"
        )
        val sizeOfClientDataBaseBeforeClientRegistered = mockDataSource.getClients().size

        // when
        val registeredClient = mockDataSource.registerClient(newClient)

        // then
        assertThat(newClient).isEqualTo(registeredClient)
        assertThat(mockDataSource.getClients()).contains(newClient)
        assertThat(mockDataSource.getClients().size).isGreaterThan(sizeOfClientDataBaseBeforeClientRegistered)
    }

    @Test
    fun `should throw DuplicateClientIdException when the client registers with an existing clientID` () {

        // given
        val newClient = Client(
            1, "m", "m", "newexample@email", "123"
        )

        assertThat(mockDataSource.getClients().find { it.clientId == 1 }).isNotNull

        // When/Then
        assertThatThrownBy { mockDataSource.registerClient(newClient) }
            .isInstanceOf(DuplicateClientIdException :: class.java)
            .hasMessage("Client id must be unique")

    }

    @Test
    fun `should throw DuplicateClientEmailException when the client registers with an existing email` () {
        // given
        val newClient = Client(
            10, "m", "m", "example@email", "123"
        )

        assertThat(mockDataSource.getClients().find { it.email == "example@email" }).isNotNull

        // When/Then
        assertThatThrownBy { mockDataSource.registerClient(newClient) }
            .isInstanceOf(ClientAlreadyExistsException :: class.java)
            .hasMessage("There already contains a user with email: ${newClient.email}")
    }

    @Test
    fun `should throw InvalidClientCredentialsException when client registers without an email, first name, last name or a password` () {
        // given
        val newClient = Client(
            1, "", "", "", ""
        )


        // When/Then
        assertThatThrownBy { mockDataSource.registerClient(newClient) }
            .isInstanceOf(InvalidClientCredentialsException :: class.java)
            .hasMessage("Client must contain a first name, last name, an email and a password")

    }
}
