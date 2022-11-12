package com.silverbullet.core.data.interfaces

import com.silverbullet.feature_comment.data.model.Comment

interface CommentsRepository {

    suspend fun createComment(
        text: String,
        postId: String,
        userId: String
    ): Boolean

    suspend fun getComments(postId: String): List<Comment>

    suspend fun commentsCount(postId: String): Long
}