package com.silverbullet.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class TokenServiceImpl : TokenService {

    override suspend fun generate(tokenConfig: TokenConfig, vararg claims: TokenClaim): String {
        var token = JWT
            .create()
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withExpiresAt(
                Date(System.currentTimeMillis() + tokenConfig.expiresIn)
            )
        claims.forEach { claim ->
            token = token.withClaim(claim.name, claim.value)
        }
        return token
            .sign(Algorithm.HMAC256(tokenConfig.secret))
    }
}