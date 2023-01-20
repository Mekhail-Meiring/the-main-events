package za.co.themainevents.api

import org.springframework.stereotype.Repository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Repository
class ApiHandler {

    fun createClientServiceApi(clientServiceAddress: String): ClientServiceApiInterface {

        val clientServiceUrl = "$clientServiceAddress/client/"

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(clientServiceUrl)
            .build()
            .create(ClientServiceApiInterface::class.java)
    }
}