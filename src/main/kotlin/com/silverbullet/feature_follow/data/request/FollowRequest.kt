package com.silverbullet.feature_follow.data.request

import kotlinx.serialization.Serializable

@Serializable
data class FollowRequest(
    val followedUserId: String
)
