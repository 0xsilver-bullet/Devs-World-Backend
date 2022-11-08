package com.silverbullet.feature_user.route

import com.silverbullet.feature_user.data.UserRepository
import com.silverbullet.feature_user.data.request.LoginRequest
import com.silverbullet.feature_user.service.UserService
import com.silverbullet.security.token.TokenClaim
import com.silverbullet.security.token.TokenConfig
import com.silverbullet.security.token.TokenService
import com.silverbullet.utils.failureBasicResponse
import com.silverbullet.utils.parsingFailureResponse
import com.silverbullet.utils.successfulBasicResponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.loginRoute() {

    val userService: UserService by inject()
    val tokenService: TokenService by inject()
    val tokenConfig: TokenConfig by inject()

    post("login") {
        kotlin.runCatching {
            call.receive<LoginRequest>()
        }.apply {
            onSuccess { request: LoginRequest ->
                when (val result = userService.loginUser(request)) {
                    is UserRepository.LoginResult.Success -> {
                        val token = tokenService.generate(
                            tokenConfig = tokenConfig,
                            TokenClaim("userid", result.userId)
                        )
                        call.successfulBasicResponse(
                            data = mapOf("token" to token)
                        )
                    }

                    UserRepository.LoginResult.WrongCredentials -> {
                        call.failureBasicResponse<Unit>(
                            message = "Invalid Credentials"
                        )
                    }
                }
            }
            onFailure {
                call.parsingFailureResponse()
            }
        }
    }
}