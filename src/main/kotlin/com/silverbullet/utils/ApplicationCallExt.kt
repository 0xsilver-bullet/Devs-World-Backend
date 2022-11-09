package com.silverbullet.utils

import com.silverbullet.core.data.response.BasicResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*


suspend fun ApplicationCall.parsingFailureResponse() {
    respond(
        status = HttpStatusCode.BadRequest,
        message = BasicResponse<Unit>(successful = false, message = "Failed to parse json request")
    )
}

suspend inline fun <reified T> ApplicationCall.successfulBasicResponse(
    message: String? = null,
    data: T? = null
) {
    val response = BasicResponse(
        successful = true,
        message = message,
        data = data
    )
    respond(
        status = HttpStatusCode.OK,
        message = response
    )
}

suspend inline fun <reified T> ApplicationCall.failureBasicResponse(
    statusCode: HttpStatusCode = HttpStatusCode.Conflict,
    message: String? = null,
    data: T? = null
) {
    val response = BasicResponse(
        successful = false,
        message = message,
        data = data
    )
    respond(
        status = statusCode,
        message = response
    )
}

val ApplicationCall.userId: String
    get() = principal<JWTPrincipal>()?.getClaim("userId", String::class).toString()