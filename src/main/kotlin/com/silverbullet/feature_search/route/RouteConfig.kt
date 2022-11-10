package com.silverbullet.feature_search.route

import io.ktor.server.routing.*

fun Route.configureSearchRoutes(){

    route("/api/search"){

        searchUsersRoute()
    }
}