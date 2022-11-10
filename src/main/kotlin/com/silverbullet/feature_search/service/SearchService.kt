package com.silverbullet.feature_search.service

import com.silverbullet.core.data.interfaces.UserRepository
import com.silverbullet.feature_user.data.response.ProfileResponse

class SearchService(
    private val usersRepository: UserRepository
) {

    suspend fun searchUsers(query: String): List<ProfileResponse> {
        if (query.isBlank()) {
            return emptyList()
        }
        val results = mutableListOf<ProfileResponse>()
        usersRepository
            .getUserByEmail(query)
            ?.let { results.add(ProfileResponse.fromUserEntity(it, isOwnProfile = false)) }
        return results.toList()
    }
}