package com.silverbullet.feature_post.data.model

import com.silverbullet.core.data.entity.PostEntity
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: String,
    val imageUrl: String,
    val description: String,
    val timestamp: Long,
    val userId: String,
    val isLiked: Boolean,
    val username: String,
    val likesCount: Long,
    val commentsCount: Long
) {
    companion object {

        fun fromPostEntity(
            postEntity: PostEntity,
            username: String,
            isLiked: Boolean,
            likesCount: Long,
            commentsCount: Long
        ): Post {
            return Post(
                id = postEntity.id,
                imageUrl = postEntity.imageUrl,
                description = postEntity.description,
                timestamp = postEntity.timestamp,
                userId = postEntity.userId,
                isLiked = isLiked,
                username = username,
                likesCount = likesCount,
                commentsCount = commentsCount
            )
        }
    }
}
