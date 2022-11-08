package com.silverbullet.feature_user.data.entity

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class UserEntity(
    val email: String,
    val username: String,
    val password: String,
    val salt: String,
    val bio: String = "",
    val profileImageUrl: String? =null,
    val skills: List<String> = emptyList(),
    val githubUrl: String? = null,
    val linkedinUrl: String? = null,
    val instagramUrl: String? = null,
    @BsonId
    val id: String = ObjectId().toString()
)