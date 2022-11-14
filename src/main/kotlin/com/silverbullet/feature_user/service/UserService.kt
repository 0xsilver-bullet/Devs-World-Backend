package com.silverbullet.feature_user.service

import com.silverbullet.core.data.entity.UserEntity
import com.silverbullet.core.data.interfaces.FollowingRepository
import com.silverbullet.core.data.interfaces.UserRepository
import com.silverbullet.feature_user.data.request.CreateUserRequest
import com.silverbullet.feature_user.data.request.LoginRequest
import com.silverbullet.feature_user.data.request.UpdateUserRequest
import com.silverbullet.feature_user.data.response.ProfileResponse

class UserService(
    private val userRepository: UserRepository,
    private val followingRepository: FollowingRepository
) {

    suspend fun createUser(request: CreateUserRequest): UserRepository.CreateUserResult {
        return userRepository.createUser(
            username = request.username,
            email = request.email,
            password = request.password
        )
    }

    suspend fun loginUser(request: LoginRequest): UserRepository.LoginResult {
        return userRepository.loginUser(
            email = request.email,
            password = request.password
        )
    }

    suspend fun getUserProfile(targetId: String, userId: String): ProfileResponse? {
        val targetUser = userRepository.getUserById(targetId)
        val isOwnProfile = targetUser?.id == userId
        val isFollowed = if (isOwnProfile) {
            null
        } else {
            val followedByUser = followingRepository.getUserFollows(userId)
            followedByUser.find { it.followedUserId == targetId } != null
        }
        return targetUser?.let {
            ProfileResponse.fromUserEntity(
                it,
                isOwnProfile,
                isFollowed
            )
        }
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
        val oldUserEntity = userRepository.getUserById(userId)
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
            if (userRepository.updateUser(userId, user)) {
                clearCallback(oldUserEntity)
                true
            } else false
        } ?: false
        return updated
    }

}