package com.silverbullet.core.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class CommentEntity(
    val text: String,
    val timestamp: Long,
    val postId: String,
    val userId: String,
    val username: String,
    val likesCount: Int = 0,
    @BsonId
    val id: String = ObjectId().toString()
) {
    companion object {
        fun create(
            text: String,
            postId: String,
            userId: String,
            username: String
        ): CommentEntity {
            return CommentEntity(
                text = text,
                timestamp = System.currentTimeMillis(),
                postId = postId,
                userId = userId,
                username = username
            )
        }
    }
}