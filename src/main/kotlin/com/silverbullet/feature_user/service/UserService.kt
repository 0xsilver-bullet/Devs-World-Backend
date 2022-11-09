package com.silverbullet.feature_user.service

import com.silverbullet.feature_user.data.UserRepository
import com.silverbullet.feature_user.data.entity.UserEntity
import com.silverbullet.feature_user.data.entity.toProfileResponse
import com.silverbullet.feature_user.data.request.CreateUserRequest
import com.silverbullet.feature_user.data.request.LoginRequest
import com.silverbullet.feature_user.data.request.UpdateUserRequest
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

    /**
     * @param clearCallback Callback gives the old user data in case you need to clear something
     *
     */
    suspend fun updateUser(
        userId: String,
        request: UpdateUserRequest,
        profileImageUrl: String?,
        profileImageInternalPath: String?,
        clearCallback: suspend (UserEntity) -> Unit = {}
    ): Boolean {
        val oldUserEntity = repository.getUserById(userId)
        val newUser = oldUserEntity?.copy(
            username = request.username,
            githubUrl = request.githubUrl,
            linkedinUrl = request.linkedinUrl,
            instagramUrl = request.instagramUrl,
            bio = request.bio,
            skills = request.skills,
            profileImageUrl = profileImageUrl ?: oldUserEntity.profileImageUrl,
            profileImageInternalPath = profileImageInternalPath ?: oldUserEntity.profileImageInternalPath
        )
        val updated = newUser?.let { user ->
            if (repository.updateUser(userId, user)) {
                clearCallback(oldUserEntity)
                true
            } else false
        } ?: false
        return updated
    }

}