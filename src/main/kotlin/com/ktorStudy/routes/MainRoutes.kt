package com.ktorStudy.routes

import com.ktorStudy.models.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*

fun Route.mainRouting() {
    static("/static") {
        resources("files")
    }
    get("/") {
        call.respond(FreeMarkerContent("index.ftl", mapOf("entries" to blogEntries), ""))
    }
}

fun Route.postSubmit() {
    post("/submit") {
        val params = call.receiveParameters()
        val headline = params["headline"] ?: return@post call.respond(HttpStatusCode.BadRequest)
        val body = params["body"] ?: return@post call.respond(HttpStatusCode.BadRequest)
        val newEntry = BlogEntry(headline, body)
        blogEntries.add(0, newEntry)
        call.respondHtml {
            body {
                h1 {
                    +"Thanks for submitting your entry!"
                }
                p {
                    +"We've submitted your new entry titled "
                    b {
                        +newEntry.headline
                    }
                }
                p {
                    +"You have submitted a total of ${blogEntries.count()} articles!"
                }
                a("/") {
                    +"Go back"
                }
            }
        }
    }
}


fun Application.registerMainRoutes() {
    routing {
        mainRouting()
        postSubmit()
    }
}