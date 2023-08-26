package routes

import dto.UserScore
import exception.UserNotFoundException
import exception.UserWithGivenEmailAlreadyExistsException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.UserScoreServiceImpl
import utils.getDashedTenantId

fun Route.userScoreRoute() {
    val userScoreService = UserScoreServiceImpl()

    get("score/{userEmail}/{language}") {
        val userEmail = call.parameters["userEmail"].toString()
        val language = call.parameters["language"].toString()
        val tenantId = getDashedTenantId(call.request.header("authorization")!!)

        try {
            call.respond(userScoreService.getUserScore(tenantId, userEmail, language))
        } catch (e: UserNotFoundException) {
            call.respond(HttpStatusCode.NotFound, e.message.toString())
        }
    }

    put("score") {
        val updatedUserScore = call.receive<UserScore>()
        val tenantId = getDashedTenantId(call.request.header("authorization")!!)

        try {
            call.respond(userScoreService.updateUserScore(tenantId, updatedUserScore))
        } catch (e: UserNotFoundException) {
            call.respond(HttpStatusCode.NotFound, e.message.toString())
        } catch (e: UserWithGivenEmailAlreadyExistsException) {
            call.respond(HttpStatusCode.Conflict, e.message.toString())
        }
    }

    post("score") {
        val newUserScore = call.receive<UserScore>()
        val tenantId = getDashedTenantId(call.request.header("authorization")!!)

        try {
            call.respond(userScoreService.addUserScore(tenantId, newUserScore))
        } catch (e: UserNotFoundException) {
            call.respond(HttpStatusCode.NotFound, e.message.toString())
        }
    }

}