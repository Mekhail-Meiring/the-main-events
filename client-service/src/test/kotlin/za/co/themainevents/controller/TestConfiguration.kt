package za.co.themainevents.controller

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import za.co.themainevents.datasource.Datasource
import za.co.themainevents.datasource.mock.MockClientDataSource

@TestConfiguration
class TestConfiguration {

    @Bean
    @Primary
    @Qualifier("clientDatasource")
    fun mockDatasource(): Datasource {
        return MockClientDataSource()
    }
}