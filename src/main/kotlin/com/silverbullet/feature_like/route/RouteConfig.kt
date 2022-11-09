package com.silverbullet.feature_like.route

import io.ktor.server.routing.*

fun Route.configureLikeRoutes() {
    route("/api/likes") {
        likeRoute()
        unlikeRoute()
        likesOfParent()
    }
}