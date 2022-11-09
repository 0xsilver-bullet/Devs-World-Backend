package com.silverbullet.feature_user.data.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val username: String,
    val githubUrl: String,
    val linkedinUrl: String,
    val instagramUrl: String,
    val bio: String,
    val skills: List<String>,
)
