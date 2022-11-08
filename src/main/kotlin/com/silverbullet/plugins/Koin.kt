package com.silverbullet.plugins

import com.silverbullet.di.mainModule
import com.silverbullet.di.repositoriesModule
import com.silverbullet.di.servicesModule
import com.silverbullet.security.token.TokenConfig
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(
            module {
                single {
                    generateTokenConfig()
                }
            },
            mainModule,
            repositoriesModule,
            servicesModule
        )
    }
}

/**
 * Generates the token config to be injected in token service and authentication plugin.
 */
fun Application.generateTokenConfig(): TokenConfig {
    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()
    return TokenConfig(
        issuer = jwtIssuer,
        audience = jwtAudience,
        secret = jwtSecret,
        expiresIn = System.currentTimeMillis() + (1000L * 60L * 60L * 24L * 365L)
    )
}