package za.co.themainevents.datasource.dto

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table


object FriendsClientIDsDO : Table("friends_client_ids") {
    val clientId = integer("client_id").references(ClientDO.clientId, onDelete = ReferenceOption.CASCADE)
    val friendId = integer("friend_id").references(ClientDO.clientId, onDelete = ReferenceOption.CASCADE)
}