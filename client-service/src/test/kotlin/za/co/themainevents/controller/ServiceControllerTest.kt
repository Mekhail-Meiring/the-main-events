package za.co.themainevents.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
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


    @Nested
    @DisplayName("A client can register, login, create events and add friends")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation::class)
    inner class BasicFunctionality {

        // Given:
        private val newClientName = "newJohn"
        private val newClientSurname = "Doe"
        private val newClientEmail = "newexample@email"
        private val newClientPassword = "password"
        private val newClientID = 0


        @Order(1)
        @Test
        fun `first time client should be able to register` () {

            // Given
            val client = Client(newClientID, newClientName, newClientSurname, newClientEmail, newClientPassword)

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

        @Order(2)
        @Test
        fun `registered client should be added to the database` () {
            // when/then
            mockMvc.get("/clients")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[3].first_name") {value(newClientName) }
                }
        }


        @Order(3)
        @Test
        fun `registered client should be able to login` () {

            // When
            val performPost = mockMvc.post("/login"){
                contentType = MediaType.APPLICATION_JSON
                param("email", newClientEmail)
                param("password", newClientPassword)
            }

            // Then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.first_name") {value(newClientName)}
                }
        }

        @Order(4)
        @Test
        fun `should be able to create a new event` () {

            // Given
            val event = Event(0, newClientID, "newLocation", "newData", "newTime", "")

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


        @Order(5)
        @Test
        fun `should be able to add friends` () {
            // Given
            val friendID = 1

            // When
            val performPost = mockMvc.post("/add-friend"){
                contentType = MediaType.APPLICATION_JSON
                param("clientID", newClientID.toString())
                param("friendID", friendID.toString())
            }

            // Then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.client_id") {value(newClientID)}
                }

        }
    }

}