package com.silverbullet.feature_search.route

import com.silverbullet.feature_search.service.SearchService
import com.silverbullet.utils.successfulBasicResponse
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.searchUsersRoute() {

    val searchService: SearchService by inject()

    authenticate {
        get("/user") {

            val query = call.parameters["query"]
            val users = query?.let {
                searchService.searchUsers(it)
            } ?: emptyList()

            call.successfulBasicResponse(data = users)
        }
    }
}