package za.co.themainevents.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event

interface ClientServiceApiInterface {

    @POST("login")
    fun clientLogin(): Call<List<Event>>

    @POST("register")
    fun clientRegister(): Call<List<Event>>

    @GET("events")
    fun getOtherEvents(): Call<List<Event>>

    @GET("friends")
    fun getFriendsList(): Call<List<Client>>


}