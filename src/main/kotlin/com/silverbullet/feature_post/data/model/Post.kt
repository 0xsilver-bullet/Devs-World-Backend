package com.silverbullet.feature_post.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val imageUrl: String,
    val description: String,
    val timestamp: Long,
    val userId: String
)
