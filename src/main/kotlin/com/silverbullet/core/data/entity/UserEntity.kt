package com.silverbullet.core.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class UserEntity(
    val email: String,
    val username: String,
    val password: String,
    val salt: String,
    val bio: String = "",
    val profileImageUrl: String? = null,
    val profileImageInternalPath: String? = null,
    val followingCount: Int = 0,
    val followersCount: Int = 0,
    val postsCount: Int = 0,
    val skills: List<String> = emptyList(),
    val githubUrl: String? = null,
    val linkedinUrl: String? = null,
    val instagramUrl: String? = null,
    @BsonId
    val id: String = ObjectId().toString()
)