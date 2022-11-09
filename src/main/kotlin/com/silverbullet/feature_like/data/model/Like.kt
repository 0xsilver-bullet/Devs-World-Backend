package com.silverbullet.feature_like.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Like(
    val userId: String,
    val parentId: String,
    val parentType: Int
)
