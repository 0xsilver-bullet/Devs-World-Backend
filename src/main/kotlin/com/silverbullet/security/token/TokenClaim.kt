package com.silverbullet.security.token

/**
 *  key,value pairs used to store data in the token
 */
data class TokenClaim(
    val name: String,
    val value: String
)
