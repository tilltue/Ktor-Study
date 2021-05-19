package com.ktorStudy

import com.ktorStudy.routes.registerAuthRoutes
import com.ktorStudy.routes.registerCustomerRoutes
import com.ktorStudy.routes.registerOrderRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.auth.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        json()
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
    }
    registerCustomerRoutes()
    registerOrderRoutes()
    registerAuthRoutes()
}