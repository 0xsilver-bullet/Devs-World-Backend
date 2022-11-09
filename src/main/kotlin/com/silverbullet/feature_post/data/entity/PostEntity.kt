package com.silverbullet.feature_post.data.entity

import com.silverbullet.feature_post.data.model.Post
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class PostEntity(
    val imageUrl: String,
    val imageInternalPath: String,
    val userId: String,
    val timestamp: Long,
    val description: String,
    @BsonId
    val id: String = ObjectId().toString()
)

fun PostEntity.toPost(): Post {
    return Post(
        imageUrl = imageUrl,
        description = description,
        timestamp = timestamp,
        userId = userId
    )
}