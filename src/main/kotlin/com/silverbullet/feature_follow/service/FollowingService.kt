package com.silverbullet.feature_follow.service

import com.silverbullet.core.data.interfaces.FollowingRepository
import com.silverbullet.core.data.entity.FollowingEntity

class FollowingService(
    private val repository: FollowingRepository
) {

    suspend fun followUser(
        followingUserId: String,
        followedUserId: String
    ): Boolean {
        if (followingUserId == followedUserId) {
            return false
        }
        val followingEntity = FollowingEntity(
            followingUserId = followingUserId,
            followedUserId = followedUserId
        )
        return repository.createFollowing(followingEntity)
    }

    suspend fun unfollowUser(
        followingUserId: String,
        followedUserId: String
    ): Boolean {
        return repository.deleteFollowing(followingUserId, followedUserId)
    }
}