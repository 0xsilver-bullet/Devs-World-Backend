package com.silverbullet.feature_follow.route

import io.ktor.server.routing.*

fun Route.configureFollowingRoutes(){
    route("/api/following"){
        followUserRoute()
        unfollowUserRoute()
    }
}