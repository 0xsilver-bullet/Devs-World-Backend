package com.silverbullet.feature_activity.route

import com.silverbullet.feature_activity.service.ActivityService
import com.silverbullet.utils.successfulBasicResponse
import com.silverbullet.utils.userId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.getUserActivitiesRoute() {

    val activityService: ActivityService by inject()

    authenticate {
        get {
            val activities = activityService.getUserActivities(call.userId)
            call.successfulBasicResponse(data = activities)
        }
    }
}