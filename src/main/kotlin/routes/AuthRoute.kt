package routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.applicationConfig
import exception.UserNotFoundException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.AuthServiceImpl
import utils.getDashedTenantId
import java.security.MessageDigest
import java.sql.Date

fun Route.authRoute() {
    val authService = AuthServiceImpl()
    post("auth/{userEmail}/{password}") {
        val email = call.parameters["userEmail"].toString()
        val password = call.parameters["password"].toString()
        val token = generateJwtToken(email)
        val tenantId = getDashedTenantId(token)

        try {
            authService.loginUser(tenantId, email, password)
            call.respond(HttpStatusCode.OK, mapOf("token" to token))
        } catch (e: UserNotFoundException) {
            call.respond(HttpStatusCode.NotFound, e.message.toString())
        }
    }
}

fun generateJwtToken(username: String): String {
    val expiration = Date(System.currentTimeMillis() + 3600000) // Token expiration time (e.g., 1 hour)
    return JWT.create()
        .withSubject(username)
        .withExpiresAt(expiration)
        .withClaim("tenantId", generateTenantIdFromEmail(username))
        .sign(Algorithm.HMAC256(applicationConfig.property("jwt.signingKey").getString()))
}


fun generateTenantIdFromEmail(email: String): String {
    val emailBytes = email.toByteArray()

    // Use a hashing algorithm (e.g., SHA-256) to create a hash of the email
    val digest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = digest.digest(emailBytes)

    // Convert the hashed bytes to a hexadecimal string
    return hashedBytes.joinToString("") { "%02x".format(it) }.substring(0, 32)
}
