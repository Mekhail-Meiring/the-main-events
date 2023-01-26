package za.co.themainevents.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import za.co.themainevents.datasource.Datasource
import za.co.themainevents.datasource.dto.ListOfClients
import za.co.themainevents.datasource.dto.ListOfEvents
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event

@Service
class WebService (@Qualifier("network") private val datasource: Datasource) {
    fun getFriends(clientID: Int): ListOfClients = datasource.getFriends(clientID)
    fun getOtherClientsEvents(): ListOfEvents = datasource.getAllEvents()
    fun getClientEvents(clientID: Int): ListOfEvents = datasource.getClientEvents(clientID)
    fun createEvent(event: Event) = datasource.createNewEvent(event)
    fun registerClient(client: Client) = datasource.registerUser(client)
    fun clientLogin(client: Client) = datasource.clientLogin(client)
    fun getAllClients(): ListOfClients = datasource.getClients()

}