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
            val userId = call.parameters[QueryParams.PARAM_USER_ID] ?: call.userId
            val posts = postsService.getAllPosts(userId)
            call.successfulBasicResponse(data = posts)
        }
    }
}