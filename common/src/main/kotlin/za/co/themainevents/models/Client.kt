package za.co.themainevents.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Client(

    @JsonProperty("client_id")
    val clientId: Int,

    @JsonProperty("first_name")
    val firstName: String,

    @JsonProperty("last_name")
    val lastName: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("password")
    val password: String,
    )
