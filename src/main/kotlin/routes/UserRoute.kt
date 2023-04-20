package routes

import dto.User
import dto.UserDraft
import exception.UserNotFoundException
import exception.UserWithGivenEmailAlreadyExistsException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.UserServiceImpl
import utils.getDashedTenantId

fun Route.usersRoute() {
    val userService = UserServiceImpl()
        post("users") {
            val requestBody = call.receive<UserDraft>()
            val tenantId = getDashedTenantId(call.request.header("authorization")!!)

            try {
                call.respond(userService.createUser(tenantId, requestBody))
            } catch (e: UserWithGivenEmailAlreadyExistsException) {
                call.respond(HttpStatusCode.Conflict, e.message.toString())
            }
        }

         put("users/{id}") {
             val updatedUser = call.receive<User>()
             val tenantId = getDashedTenantId(call.request.header("authorization")!!)

            try {
                call.respond(userService.updateUser(tenantId, updatedUser))
            } catch (e: UserNotFoundException) {
                call.respond(HttpStatusCode.NotFound, e.message.toString())
            } catch (e: UserWithGivenEmailAlreadyExistsException) {
                call.respond(HttpStatusCode.Conflict, e.message.toString())
            }
        }

        get("users/{email}/{password}") {
            val email = call.parameters["email"].toString()
            val password = call.parameters["password"].toString()
            val tenantId = getDashedTenantId(call.request.header("authorization")!!)

            try {
                call.respond(userService.loginUser(tenantId, email, password))
            } catch (e: UserNotFoundException) {
                call.respond(HttpStatusCode.NotFound, e.message.toString())
            }
        }
}