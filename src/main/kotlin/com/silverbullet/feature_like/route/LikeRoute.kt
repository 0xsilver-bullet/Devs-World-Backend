package com.silverbullet.feature_like.route

import com.silverbullet.feature_like.data.request.LikeRequest
import com.silverbullet.feature_like.service.LikesService
import com.silverbullet.utils.failureBasicResponse
import com.silverbullet.utils.parsingFailureResponse
import com.silverbullet.utils.successfulBasicResponse
import com.silverbullet.utils.userId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * Allows user to like a post or comment
 */
fun Route.likeRoute() {

    val likesService: LikesService by inject()

    authenticate {
        post("/like") {
            kotlin.runCatching {
                call.receive<LikeRequest>()
            }.apply {
                onSuccess { request: LikeRequest ->
                    val confirmed = likesService.like(
                        userId = call.userId,
                        request = request
                    )
                    if (confirmed) {
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