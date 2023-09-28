package routes

import dto.UserVisibleData
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
        get("users/{email}") {
            val email = call.parameters["email"].toString()
            val tenantId = getDashedTenantId(call.request.header("authorization")!!)

            try {
                if (userService.getUser(tenantId, email) != null){
                    call.respond(userService.getUser(tenantId, email)!!)
                }
            } catch (e: UserNotFoundException) {
                call.respond(HttpStatusCode.NotFound, e.message.toString())
            }
        }

         put("users/{email}") {
             val updatedUser = call.receive<UserVisibleData>()
             val tenantId = getDashedTenantId(call.request.header("authorization")!!)

            try {
                call.respond(userService.updateUser(tenantId, updatedUser))
            } catch (e: UserNotFoundException) {
                call.respond(HttpStatusCode.NotFound, e.message.toString())
            } catch (e: UserWithGivenEmailAlreadyExistsException) {
                call.respond(HttpStatusCode.Conflict, e.message.toString())
            }
        }

    put("users/{email}/{password}/{newPassword}/change") {
        val email = call.parameters["email"].toString()
        val password = call.parameters["password"].toString()
        val newPassword = call.parameters["newPassword"].toString()


        val tenantId = getDashedTenantId(call.request.header("authorization")!!)

        try {
            call.respond(userService.updatePassword(tenantId, email, password, newPassword))
        } catch (e: UserNotFoundException) {
            call.respond(HttpStatusCode.NotFound, e.message.toString())
        }
    }
}