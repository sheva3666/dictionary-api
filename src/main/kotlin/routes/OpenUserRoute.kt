package routes

import dto.UserDraft
import exception.UserWithGivenEmailAlreadyExistsException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.UserServiceImpl
import java.util.*

fun Route.openUsersRoute() {
    val userService = UserServiceImpl()
        post("users") {
            val requestBody = call.receive<UserDraft>()
            val nonDashedTenantID = generateTenantIdFromEmail(requestBody.email)
            val tenantId = UUID.fromString(StringBuilder(nonDashedTenantID)
                .insert(8, "-")
                .insert(13, "-")
                .insert(18, "-")
                .insert(23, "-")
                .toString())

            try {
                call.respond(userService.createUser(tenantId, requestBody))
            } catch (e: UserWithGivenEmailAlreadyExistsException) {
                call.respond(HttpStatusCode.Conflict, e.message.toString())
            }
        }
}