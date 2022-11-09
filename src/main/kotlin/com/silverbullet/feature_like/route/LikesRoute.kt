package com.silverbullet.feature_like.route

import com.silverbullet.feature_like.service.LikesService
import com.silverbullet.utils.QueryParams
import com.silverbullet.utils.failureBasicResponse
import com.silverbullet.utils.successfulBasicResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * Sends a list of likes on a parent
 */
fun Route.likesOfParent() {

    val likesService: LikesService by inject()

    get("/{parentId}") {
        val parentId = call.parameters[QueryParams.PARAM_PARENT_ID]
        if (parentId.isNullOrBlank()) {
            call.failureBasicResponse<Unit>(statusCode = HttpStatusCode.BadRequest)
            return@get
        }
        val likes = likesService.getLikesForParent(parentId = parentId)
        call.successfulBasicResponse(data = likes)
    }
}