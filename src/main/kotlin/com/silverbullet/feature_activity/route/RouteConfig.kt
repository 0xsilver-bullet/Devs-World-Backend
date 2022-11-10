package com.silverbullet.feature_activity.route

import io.ktor.server.routing.*

fun Route.configureActivitiesRoutes() {
    route("/api/activity") {
        getUserActivitiesRoute()
    }
}