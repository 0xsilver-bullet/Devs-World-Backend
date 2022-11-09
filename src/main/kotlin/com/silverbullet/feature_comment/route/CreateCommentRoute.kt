package com.silverbullet.feature_comment.route

import com.silverbullet.feature_comment.data.request.CreateCommentRequest
import com.silverbullet.feature_comment.service.CommentsService
import com.silverbullet.utils.failureBasicResponse
import com.silverbullet.utils.parsingFailureResponse
import com.silverbullet.utils.successfulBasicResponse
import com.silverbullet.utils.userId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.createCommentRoute() {

    val commentsService: CommentsService by inject()

    authenticate {
        post("/create") {
            kotlin.runCatching {
                call.receive<CreateCommentRequest>()
            }.apply {
                onSuccess { request: CreateCommentRequest ->
                    if (commentsService.createComment(request, call.userId)) {
                        call.successfulBasicResponse<Unit>()
                    } else {
                        call.failureBasicResponse<Unit>()
                    }
                }
                onFailure {
                    call.parsingFailureResponse()
                }
            }
        }
    }
}