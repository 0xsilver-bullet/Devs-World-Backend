package com.silverbullet.core.data.interfaces

import com.silverbullet.core.data.entity.FollowingEntity

interface FollowingRepository {

    suspend fun createFollowing(following: FollowingEntity): Boolean

    suspend fun deleteFollowing(
        followingUserId: String,
        followedUserId: String
    ): Boolean

    /**
     * @return a list of users followed by this user
     */
    suspend fun getUserFollows(userId: String): List<FollowingEntity>
}