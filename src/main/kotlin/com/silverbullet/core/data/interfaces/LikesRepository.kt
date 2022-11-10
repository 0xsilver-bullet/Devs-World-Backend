package com.silverbullet.core.data.interfaces

import com.silverbullet.feature_like.data.model.Like
import com.silverbullet.feature_like.data.util.LikeParentType

interface LikesRepository {

    suspend fun placeLike(
        userId: String,
        parentId: String,
        parentType: LikeParentType
    ): Boolean

    suspend fun removeLike(
        userId: String,
        parentId: String,
        parentType: LikeParentType
    ): Boolean

    suspend fun getLikes(parentId: String): List<Like>

}