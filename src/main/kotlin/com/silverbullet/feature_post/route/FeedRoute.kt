package com.silverbullet.feature_post.route

import com.silverbullet.feature_post.service.PostService
import com.silverbullet.utils.QueryParams
import com.silverbullet.utils.successfulBasicResponse
import com.silverbullet.utils.userId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.feedRoute() {

    val postService: PostService by inject()

    authenticate {

        get("/feed") {

            val page = call.parameters[QueryParams.PARAM_PAGE_NUMBER]?.toIntOrNull()
            val offset = call.parameters[QueryParams.PARAM_PAGE_OFFSET]?.toIntOrNull()

            val posts = postService.getFeed(
                userId = call.userId,
                page = page,
                offset = offset
            )
            call.successfulBasicResponse(data = posts)
        }
    }
}