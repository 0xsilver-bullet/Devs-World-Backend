package com.silverbullet.security.token

/**
 * @param issuer identifies who issued  this token
 * @param audience allows different types of tokens for different types of audience ( normal user or admin )
 */
data class TokenConfig(
    val issuer: String,
    val audience: String,
    val expiresIn: Long,
    val secret: String
)
