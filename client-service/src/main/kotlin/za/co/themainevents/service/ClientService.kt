package za.co.themainevents.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import za.co.themainevents.datasource.Datasource
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event


@Service
class ClientService (@Qualifier("clientDatasource") private val dataSource: Datasource){

    fun getClients() : List<Client> = dataSource.getClients()

    fun getAllEvents() : List<Event> = dataSource.getAllEvents()

    fun registerClient(client: Client) = dataSource.registerClient(client)

    fun createNewEvent(event: Event) : Event = dataSource.createNewEvent(event)

    fun clientLogin(email: String, password: String) : Client = dataSource.clientLogin(email, password)

    fun getClientFriends(clientId: Int) : List<Client> = dataSource.getFriends(clientId)

    fun getEventsForClient(clientId: Int) : List<Event> = dataSource.getClientEvents(clientId)

    fun updateEvent(newEvent: Event, clientId: Int) : Event = dataSource.updateEvent(newEvent, clientId)

    fun updateClientInformation(client: Client) : Client = dataSource.updateClientInformation(client)

    fun removeEvent(eventID: Int, clientId: Int) = dataSource.removeEvent(eventID, clientId)

    fun removeClient(clientID: Int)  = dataSource.removeClient(clientID)

}