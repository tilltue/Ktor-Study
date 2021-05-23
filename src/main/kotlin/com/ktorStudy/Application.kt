package com.ktorStudy

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ktorStudy.routes.registerAuthRoutes
import com.ktorStudy.routes.registerCustomerRoutes
import com.ktorStudy.routes.registerMainRoutes
import com.ktorStudy.routes.registerOrderRoutes
import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.freemarker.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        json()
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
    install(Authentication) {
        basic("auth-basic") {
            realm = "Access to the '/' path"
            validate { credentials ->
                if (credentials.name == "jetbrains" && credentials.password == "foobar") {
                    UserIdPrincipal(credentials.name)
                }else {
                    null
                }
            }
        }
        val issuer = environment.config.property("jwt.domain").getString()
        val audience = environment.config.property("jwt.audience").getString()
        val jwtRealm = environment.config.property("jwt.realm").getString()
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(JWT
                .require(Algorithm.HMAC256("secret"))
                .withAudience(audience)
                .withIssuer(issuer)
                .build())
            validate { credential ->
                if (credential.payload.audience.contains(audience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
    registerCustomerRoutes()
    registerOrderRoutes()
    registerAuthRoutes()
    registerMainRoutes()
}