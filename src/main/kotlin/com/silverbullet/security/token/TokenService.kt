package com.silverbullet.security.token

interface TokenService {

    /**
     * @return JWT token
     */
    suspend fun generate(
        tokenConfig: TokenConfig,
        vararg claims: TokenClaim
    ): String
}