package com.silverbullet.feature_user.data.response

import com.silverbullet.core.data.entity.UserEntity
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val email: String,
    val username: String,
    val bio: String = "",
    val isOwnProfile: Boolean,
    val followingCount: Int,
    val followersCount: Int,
    val postsCount: Int,
    val profileImageUrl: String? = null,
    val skills: List<String> = emptyList(),
    val githubUrl: String? = null,
    val linkedinUrl: String? = null,
    val instagramUrl: String? = null
) {
    companion object {

        fun fromUserEntity(userEntity: UserEntity, isOwnProfile: Boolean): ProfileResponse {
            return ProfileResponse(
                email = userEntity.email,
                username = userEntity.username,
                bio = userEntity.bio,
                isOwnProfile = isOwnProfile,
                followingCount = userEntity.followingCount,
                followersCount = userEntity.followersCount,
                postsCount = userEntity.postsCount,
                profileImageUrl = userEntity.profileImageUrl,
                skills = userEntity.skills,
                githubUrl = userEntity.githubUrl,
                linkedinUrl = userEntity.linkedinUrl,
                instagramUrl = userEntity.instagramUrl
            )
        }
    }
}