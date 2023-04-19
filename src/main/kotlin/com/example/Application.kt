package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import configuration.DatasourceConfiguration
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import org.jooq.DSLContext

lateinit var dslContext: DSLContext

lateinit var applicationConfig: ApplicationConfig

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.main() {
    applicationConfig = environment.config
    dslContext = DatasourceConfiguration.createDSLContext()
    install(CORS) {
        allowHost("*")
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.ContentType)
    }
    configureAuthentication()
    configureSerialization()
    configureRouting()
}

fun Application.configureAuthentication() {
    install(Authentication) {
        jwt {
            verifier(
                //"jwt.signingKey" is property defined in one of application.conf file which is used at runtime
                JWT.require(Algorithm.HMAC256(applicationConfig.property("jwt.signingKey").getString())).build()
            )
            validate { jwtCredential -> JWTPrincipal(jwtCredential.payload) }
        }
    }
}
