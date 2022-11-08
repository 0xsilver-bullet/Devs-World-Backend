package com.silverbullet.plugins

import com.silverbullet.feature_user.route.configureUserRoute
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {

    routing {
        configureUserRoute()
    }
}
