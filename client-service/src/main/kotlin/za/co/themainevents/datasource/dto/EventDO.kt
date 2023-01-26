package za.co.themainevents.datasource.dto

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object EventDO : Table() {
    val eventId = integer("event_id")
    val fromClientId = integer("from_client_id").references(ClientDO.clientId, onDelete = ReferenceOption.CASCADE)
    val location = text("location")
    val date = varchar("date", 10)
    val time = varchar("time", 7)
    val description = text("description")
}