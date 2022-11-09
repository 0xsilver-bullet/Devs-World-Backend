package com.silverbullet.feature_comment.route

import io.ktor.server.routing.*

fun Route.configureCommentRoutes() {

    route("/api/comment") {

        createCommentRoute()
        commentsOnPostRoute()
    }
}