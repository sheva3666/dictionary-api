package routes

import dto.Auth
import exception.UserNotFoundException
import exception.UserWithGivenEmailAlreadyExistsException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.AuthServiceImpl
import utils.getDashedTenantId

fun Route.authRoute() {
    val authService = AuthServiceImpl()

    get("auth/{userEmail}") {
        val userEmail = call.parameters["userEmail"].toString()
        val tenantId = getDashedTenantId(call.request.header("authorization")!!)

        try {
            call.respond(authService.getAuth(tenantId, userEmail))
        } catch (e: UserNotFoundException) {
            call.respond(HttpStatusCode.NotFound, e.message.toString())
        }
    }

    put("auth/{userEmail}") {
        val updatedAuth = call.receive<Auth>()
        val tenantId = getDashedTenantId(call.request.header("authorization")!!)

        try {
            call.respond(authService.updateAuth(tenantId, updatedAuth))
        } catch (e: UserNotFoundException) {
            call.respond(HttpStatusCode.NotFound, e.message.toString())
        } catch (e: UserWithGivenEmailAlreadyExistsException) {
            call.respond(HttpStatusCode.Conflict, e.message.toString())
        }
    }

    post("auth/{userEmail}/{password}") {
        val email = call.parameters["userEmail"].toString()
        val password = call.parameters["password"].toString()
        val tenantId = getDashedTenantId(call.request.header("authorization")!!)

        try {
            call.respond(authService.loginUser(tenantId, email, password))
        } catch (e: UserNotFoundException) {
            call.respond(HttpStatusCode.NotFound, e.message.toString())
        }
    }

}