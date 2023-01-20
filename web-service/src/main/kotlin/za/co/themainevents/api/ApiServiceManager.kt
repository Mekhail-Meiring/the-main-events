package za.co.themainevents.api

import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Service class for managing API calls.
 */
@Service
class ApiServiceManager {


    /**
     * Create an instance of the client service API.
     * @param clientServiceAddress the base address of the client service API
     * @return an instance of the ClientServiceApiInterface
     */
    fun createClientServiceApi(clientServiceAddress: String): ClientServiceApiInterface {

        val clientServiceUrl = "$clientServiceAddress/client/"

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(clientServiceUrl)
            .build()
            .create(ClientServiceApiInterface::class.java)
    }
}