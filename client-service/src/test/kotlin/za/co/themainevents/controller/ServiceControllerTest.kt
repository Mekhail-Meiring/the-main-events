package za.co.themainevents.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import za.co.themainevents.models.Client
import za.co.themainevents.models.Event

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfiguration::class)
internal class ServiceControllerTest @Autowired constructor(
     val mockMvc: MockMvc,
     val objectMapper: ObjectMapper
){



    @Test
    fun `should return all clients` () {
        // when/then
        mockMvc.get("/clients")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].first_name") {value("John")}
            }
    }

    @Test
    fun `should be able to register a new client to database` () {
        // Given
        val client = Client(0, "newJohn", "Doe", "newexample@email", "password")

        // When
        val performPost = mockMvc.post("/register"){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(client)
        }

        // Then
        performPost
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.first_name") {value("newJohn")}
            }
    }
    
    
    @Test
    fun `should be able to create a new event` () {
        // Given
        val event = Event(0, 1, "newLocation", "newData", "newTime", "")
        
        // When
        val performPost = mockMvc.post("/event"){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(event)
        }
        
        // Then
        performPost
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.location") {value("newLocation")}
            }

    }


    @Test
    fun `should be able to login and get client matching information` () {
        // Given
        val email = "example@email"
        val password = "password"

        // When
        val performPost = mockMvc.post("/login"){
            contentType = MediaType.APPLICATION_JSON
            param("email", email)
            param("password", password)
        }

        // Then
        performPost
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.first_name") {value("John")}
            }

    }


    @Test
    fun `should be able to add friends` () {
        // Given
        val clientID = 1
        val friendID = 2

        // When
        val performPost = mockMvc.post("/add-friend"){
            contentType = MediaType.APPLICATION_JSON
            param("clientID", clientID.toString())
            param("friendID", friendID.toString())
        }

        // Then
        performPost
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].client_id") {value("John")}
            }

    }

}