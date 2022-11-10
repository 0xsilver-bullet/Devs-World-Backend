package com.silverbullet.feature_comment.service

import com.silverbullet.core.data.interfaces.CommentsRepository
import com.silverbullet.feature_comment.data.model.Comment
import com.silverbullet.feature_comment.data.request.CreateCommentRequest

class CommentsService(
    private val repository: CommentsRepository
    ) {

    suspend fun createComment(request: CreateCommentRequest, userId: String): Boolean {
        if (request.text.isBlank()) {
            return false
        }
        return repository.createComment(
            text = request.text,
            postId = request.postId,
            userId = userId
        )
    }

    suspend fun getComments(postId: String): List<Comment> {
        return repository.getComments(postId)
    }
}