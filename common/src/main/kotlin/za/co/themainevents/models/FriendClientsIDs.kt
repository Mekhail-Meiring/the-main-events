package za.co.themainevents.models

import com.fasterxml.jackson.annotation.JsonProperty

data class FriendClientsIDs(
    @JsonProperty("client_id")
    val clientId: Int,
    @JsonProperty("friend_id")
    val friendId: Int
)
