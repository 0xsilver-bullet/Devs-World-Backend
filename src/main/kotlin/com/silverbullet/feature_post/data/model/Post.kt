package com.silverbullet.feature_post.data.model

import com.silverbullet.core.data.entity.PostEntity
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val imageUrl: String,
    val description: String,
    val timestamp: Long,
    val userId: String
){
    companion object{

        fun fromPostEntity(postEntity: PostEntity): Post{
            return Post(
                imageUrl = postEntity.imageUrl,
                description = postEntity.description,
                timestamp = postEntity.timestamp,
                userId = postEntity.userId
            )
        }
    }
}
