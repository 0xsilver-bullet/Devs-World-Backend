package com.silverbullet.feature_post.route

import com.silverbullet.feature_post.service.PostService
import com.silverbullet.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.postDetail() {

    val postService: PostService by inject()

    authenticate {

        get {

            val postId = call.parameters[QueryParams.PARAM_POST_ID]
            if (postId.isNullOrEmpty()) {
                call.failureBasicResponse<Unit>(message = ApiResponses.NO_POST_ID)
                return@get
            }
            val post = postService.getPost(postId,call.userId)
            if (post != null) {
                call.successfulBasicResponse(data = post)
            } else {
                call.failureBasicResponse<Unit>(statusCode = HttpStatusCode.NotFound)
            }
        }
    }
}