package com.silverbullet.feature_post.data.request

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val description: String
)
