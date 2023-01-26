package za.co.themainevents.datasource.dto

import org.jetbrains.exposed.sql.Table

object ClientDO : Table() {
    val clientId = integer("client_id").autoIncrement()
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val email = varchar("email", 50)
    val password = text("password")
}