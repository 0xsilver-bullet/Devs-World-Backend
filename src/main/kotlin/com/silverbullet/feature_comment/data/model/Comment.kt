package com.silverbullet.feature_comment.data.model

import com.silverbullet.core.data.entity.CommentEntity
import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val text: String,
    val timestamp: Long,
    val postId: String,
    val userId: String,
    val username: String,
    val likesCount: Int,
    val id: String
) {

    companion object {

        fun fromCommentEntity(commentEntity: CommentEntity): Comment {
            return Comment(
                text = commentEntity.text,
                timestamp = commentEntity.timestamp,
                postId = commentEntity.postId,
                userId = commentEntity.userId,
                username = commentEntity.username,
                likesCount = commentEntity.likesCount,
                id = commentEntity.id
            )
        }
    }
}