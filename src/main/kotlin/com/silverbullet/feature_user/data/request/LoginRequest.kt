package com.silverbullet.feature_user.data.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

fun LoginRequest.hasBlankField() = email.isBlank() || password.isBlank()