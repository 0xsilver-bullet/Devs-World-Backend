package com.silverbullet.feature_comment.data.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateCommentRequest(
    val text: String,
    val postId: String
)
