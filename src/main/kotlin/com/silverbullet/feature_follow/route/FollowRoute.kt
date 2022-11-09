package com.silverbullet.feature_follow.route

import com.silverbullet.feature_follow.data.request.FollowRequest
import com.silverbullet.feature_follow.service.FollowingService
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
 * Enables user to follow each other
 */
fun Route.followUserRoute() {

    val followingService: FollowingService by inject()

    authenticate {
        post("/follow") {
            kotlin.runCatching {
                call.receive<FollowRequest>()
            }.apply {
                onSuccess { request: FollowRequest ->
                    val successful = followingService.followUser(
                        followingUserId = call.userId,
                        followedUserId = request.followedUserId
                    )
                    if (successful) {
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