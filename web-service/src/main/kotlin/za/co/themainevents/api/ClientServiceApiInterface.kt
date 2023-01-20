package za.co.themainevents.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event

/**
 * Interface for the client service API
 */
interface ClientServiceApiInterface {

    /**
     * Login a client
     * @return a call to the server to login
     */
    @POST("login")
    fun clientLogin(): Call<List<Event>>

    /**
     * Register a client
     * @return a call to the server to register a client
     */
    @POST("register")
    fun clientRegister(): Call<List<Event>>

    /**
     * Get a list of events
     * @return a call to the server to retrieve other events
     */
    @GET("events")
    fun getOtherEvents(): Call<List<Event>>

    /**
     * Get a list of friends
     * @return a call to the server to retrieve friends list
     */
    @GET("friends")
    fun getFriendsList(): Call<List<Client>>


}