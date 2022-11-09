package com.silverbullet.feature_user.data.response

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
)