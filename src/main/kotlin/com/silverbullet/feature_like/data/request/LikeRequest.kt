package com.silverbullet.feature_like.data.request

import kotlinx.serialization.Serializable

/**
 * Used for like and unlike request
 */
@Serializable
data class LikeRequest(
    val likedParentId: String,
    val parentType: Int
)
