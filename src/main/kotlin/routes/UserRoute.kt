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
             val requestBody = call.receive<User>()
             val tenantId = getDashedTenantId(call.request.header("authorization")!!)

            try {
                call.respond(userService.updateUser(tenantId, requestBody))
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
//    val userRepository: UserDao = UserDaoImpl()
//
//    get("users/{id}/{password}") {
//        val id = call.parameters["id"].toString()
//        val password = call.parameters["password"].toString()
//
//        val user = userRepository.getUser(id, password)
//
//        if (user == null) {
//            call.respond(
//                HttpStatusCode.NotFound,
//                "Email or password is incorrect"
//            )
//        } else {
//            authRepository.addAuth(user.email)
//            call.respond(user)
//        }
//    }
//
//    post ("users"){
//        val userDraft = call.receive<UserDraft>()
//        val userIsInList = userRepository.checkUser(userDraft.email)
//        if (userIsInList == null) {
//            val user = userRepository.addUser(userDraft)
//            call.respond(
//                HttpStatusCode.OK,
//                "User with  email ${user.email} created"
//            )
//        } else {
//            call.respond(
//                HttpStatusCode.OK,
//                "User with this email already registered"
//            )
//        }
//    }
//
//    put("users/{id}") {
//        val userDraft = call.receive<UserDraft>()
//        val userId = call.parameters["id"].toString()
//
//        val updated = userRepository.updateUser(userId, userDraft)
//        if (updated) {
//            call.respond(HttpStatusCode.OK, "You profile updated")
//        } else {
//            call.respond(
//                HttpStatusCode.NotFound,
//                "Something went wrong")
//        }
//    }
}