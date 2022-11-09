package com.silverbullet.feature_user.route

import com.silverbullet.feature_user.service.UserService
import com.silverbullet.utils.QueryParams
import com.silverbullet.utils.failureBasicResponse
import com.silverbullet.utils.successfulBasicResponse
import com.silverbullet.utils.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * will send the profile data for the passed user id in parameters, if no id
 * is passed it will send the sender's profile data.
 */
fun Route.getUserProfileRoute() {

    val userService: UserService by inject()

    authenticate {
        get {
            val targetId = call.parameters[QueryParams.PARAM_USER_ID] ?: call.userId
            val profileResponse = userService.getUserProfile(targetId, call.userId)
            if (profileResponse == null) {
                call.failureBasicResponse<Unit>(statusCode = HttpStatusCode.NotFound)
                return@get
            }
            call.successfulBasicResponse(data = profileResponse)
        }
    }
}