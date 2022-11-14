package com.silverbullet.feature_user.data.response

import com.silverbullet.core.data.entity.UserEntity
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val id: String,
    val email: String,
    val username: String,
    val bio: String = "",
    val isOwnProfile: Boolean,
    val isFollowed: Boolean?,
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

        fun fromUserEntity(
            userEntity: UserEntity,
            isOwnProfile: Boolean,
            isFollowed: Boolean?
        ): ProfileResponse {
            return ProfileResponse(
                id = userEntity.id,
                email = userEntity.email,
                username = userEntity.username,
                bio = userEntity.bio,
                isOwnProfile = isOwnProfile,
                isFollowed = isFollowed,
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