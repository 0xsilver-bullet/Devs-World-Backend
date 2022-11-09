package com.silverbullet.feature_user.route

import io.ktor.server.routing.*

fun Route.configureUserRoute(){
    route("/api/user"){
        createUserRoute()
        loginRoute()
        getUserProfileRoute()
    }
}