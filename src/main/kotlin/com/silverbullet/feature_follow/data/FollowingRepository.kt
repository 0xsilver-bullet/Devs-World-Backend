package com.silverbullet.feature_follow.data

import com.silverbullet.feature_follow.data.entity.FollowingEntity

interface FollowingRepository {

    suspend fun createFollowing(following: FollowingEntity): Boolean

    suspend fun deleteFollowing(
        followingUserId: String,
        followedUserId: String
    ): Boolean
}