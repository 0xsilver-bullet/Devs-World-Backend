package com.silverbullet.feature_comment.route

import com.silverbullet.feature_comment.service.CommentsService
import com.silverbullet.utils.QueryParams
import com.silverbullet.utils.failureBasicResponse
import com.silverbullet.utils.successfulBasicResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.commentsOnPostRoute() {

    val commentsService: CommentsService by inject()

    authenticate {
        get("/comments") {
            val postId = call.parameters[QueryParams.PARAM_PARENT_ID]
            if (postId.isNullOrBlank()) {
                call.failureBasicResponse<Unit>(statusCode = HttpStatusCode.BadRequest)
                return@get
            }
            val comments = commentsService.getComments(postId)
            call.successfulBasicResponse(data = comments)
        }
    }
}