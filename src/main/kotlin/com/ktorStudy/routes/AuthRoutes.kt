package com.ktorStudy.routes

import com.ktorStudy.models.Customer
import com.ktorStudy.models.customerStorage
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.registerAuthRoutes() {
    routing {
        authenticate("auth-basic") {
            get("/login") {
                call.respondText("Hello, ${call.principal<UserIdPrincipal>()?.name}")
            }
        }
    }
}