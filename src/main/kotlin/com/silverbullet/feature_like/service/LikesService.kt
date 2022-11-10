package com.silverbullet.feature_like.service

import com.silverbullet.core.data.interfaces.LikesRepository
import com.silverbullet.feature_like.data.model.Like
import com.silverbullet.feature_like.data.request.LikeRequest
import com.silverbullet.feature_like.data.util.LikeParentType

class LikesService(private val repository: LikesRepository) {

    suspend fun like(
        userId: String,
        request: LikeRequest
    ): Boolean {
        val parentType = LikeParentType.parse(request.parentType)
        if (parentType == LikeParentType.None) {
            // Not a valid parent id
            return false
        }
        return repository.placeLike(
            userId = userId,
            parentId = request.likedParentId,
            parentType = parentType
        )
    }

    suspend fun unlike(
        userId: String,
        request: LikeRequest
    ): Boolean {
        val parentType = LikeParentType.parse(request.parentType)
        if (parentType == LikeParentType.None) {
            // Not a valid parent id
            return false
        }
        return repository.removeLike(
            userId = userId,
            parentId = request.likedParentId,
            parentType = parentType
        )
    }

    suspend fun getLikesForParent(parentId: String): List<Like> {
        return repository.getLikes(parentId,)
    }
}