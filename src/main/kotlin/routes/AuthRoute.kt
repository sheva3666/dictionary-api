package routes

import dao.AuthDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoute(authRepository: AuthDao) {

    get("auth/{user}") {
        val user = call.parameters["user"].toString()


        val isAuth = authRepository.getAuth(user)

        if (isAuth == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "You should login first"
            )
        } else {
            call.respond(isAuth)
        }
    }

    delete("auth/{user}") {
        val user = call.parameters["user"].toString()

        val removed = authRepository.removeAuth(user)
        if (removed) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(
                HttpStatusCode.NotFound,
                "found no todo with the id $user")
        }
    }
}