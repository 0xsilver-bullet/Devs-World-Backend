package com.silverbullet.feature_post.route

import com.silverbullet.feature_post.service.PostService
import com.silverbullet.utils.QueryParams
import com.silverbullet.utils.successfulBasicResponse
import com.silverbullet.utils.userId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * Sends all the posts for a user id
 */
fun Route.allPostsRoute() {

    val postsService: PostService by inject()

    authenticate {
        get("/all") {
            val targetUserId = call.parameters[QueryParams.PARAM_USER_ID] ?: call.userId
            val page = call.parameters[QueryParams.PARAM_PAGE_NUMBER]?.toIntOrNull()
            val offset = call.parameters[QueryParams.PARAM_PAGE_OFFSET]?.toIntOrNull()
            val posts = postsService.getAllPosts(
                userId = call.userId,
                targetUserId = targetUserId,
                page = page,
                offset = offset
            )
            call.successfulBasicResponse(data = posts)
        }
    }
}