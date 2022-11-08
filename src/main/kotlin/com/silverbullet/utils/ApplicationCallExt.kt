package com.silverbullet.utils

import com.silverbullet.core.data.response.BasicResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*


suspend fun ApplicationCall.parsingFailureResponse() {
    respond(
        status = HttpStatusCode.BadRequest,
        message = BasicResponse<Unit>(succeeded = false, message = "Failed to parse json request")
    )
}

suspend inline fun <reified T> ApplicationCall.successfulBasicResponse(
    message: String? = null,
    data: T? = null
) {
    val response = BasicResponse(
        succeeded = true,
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
        succeeded = false,
        message = message,
        data = data
    )
    respond(
        status = statusCode,
        message = response
    )
}