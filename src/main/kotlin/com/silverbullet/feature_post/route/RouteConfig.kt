package com.silverbullet.feature_post.route

import io.ktor.server.routing.*

fun Route.configurePostRoutes() {

    route("/api/post") {

        createPostRoute()
    }
}