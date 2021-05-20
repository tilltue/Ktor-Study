package com.ktorStudy.routes

import com.ktorStudy.models.Customer
import com.ktorStudy.models.customerStorage
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
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
        authenticate("auth-jwt") {
            get("/") {
                val principal = call.authentication.principal<JWTPrincipal>()
                val subjectString = principal!!.payload.subject.removePrefix("auth0|")
                call.respondText("Hello, $subjectString")
            }
        }
    }
}