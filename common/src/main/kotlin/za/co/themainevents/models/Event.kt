package za.co.themainevents.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Event (

    @JsonProperty("event_id")
    val eventId: Int,

    @JsonProperty("from_client_id")
    val fromClientId: Int,

    @JsonProperty("location")
    val location: String,

    @JsonProperty("date")
    val date: String,

    @JsonProperty("time")
    val time: String,

    @JsonProperty("description")
    val description: String
)