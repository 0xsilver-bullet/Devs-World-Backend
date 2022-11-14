package com.silverbullet.feature_search.service

import com.silverbullet.core.data.interfaces.FollowingRepository
import com.silverbullet.core.data.interfaces.UserRepository
import com.silverbullet.feature_user.data.response.ProfileResponse

class SearchService(
    private val usersRepository: UserRepository,
    private val followingRepository: FollowingRepository
) {

    suspend fun searchUsers(userId: String,query: String): List<ProfileResponse> {
        if (query.isBlank()) {
            return emptyList()
        }
        val followedByUser = followingRepository.getUserFollows(userId)
        val results = mutableListOf<ProfileResponse>()
        usersRepository
            .getUserByEmail(query)
            ?.let {userEntity ->
                results
                    .add(
                        ProfileResponse.fromUserEntity(
                            userEntity,
                            isOwnProfile = false,
                            isFollowed = followedByUser.find { it.followedUserId == userEntity.id} != null
                        )
                    )
            }
        return results.toList()
    }
}