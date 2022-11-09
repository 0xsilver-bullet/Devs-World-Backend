package com.silverbullet.feature_user.service

import com.silverbullet.feature_user.data.UserRepository
import com.silverbullet.feature_user.data.entity.toProfileResponse
import com.silverbullet.feature_user.data.request.CreateUserRequest
import com.silverbullet.feature_user.data.request.LoginRequest
import com.silverbullet.feature_user.data.response.ProfileResponse

class UserService(private val repository: UserRepository) {

    suspend fun createUser(request: CreateUserRequest): UserRepository.CreateUserResult {
        return repository.createUser(
            username = request.username,
            email = request.email,
            password = request.password
        )
    }

    suspend fun loginUser(request: LoginRequest): UserRepository.LoginResult {
        return repository.loginUser(
            email = request.email,
            password = request.password
        )
    }

    suspend fun getUserProfile(targetId: String, userId: String): ProfileResponse? {
        val targetUser = repository.getUserById(targetId)
        val isOwnProfile = targetUser?.id == userId
        return targetUser?.toProfileResponse(isOwnProfile)
    }

}