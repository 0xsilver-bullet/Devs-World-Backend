package com.silverbullet.feature_follow.data.request

import kotlinx.serialization.Serializable

@Serializable
data class UnfollowRequest(
    val followedUserId: String
)
