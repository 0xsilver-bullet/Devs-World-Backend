package com.silverbullet.feature_comment.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val text: String,
    val timestamp: Long,
    val postId: String,
    val userId: String,
    val username: String,
    val likesCount: Int,
    val id: String
)