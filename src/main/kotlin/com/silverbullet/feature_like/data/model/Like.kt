package com.silverbullet.feature_like.data.model

import com.silverbullet.core.data.entity.LikeEntity
import kotlinx.serialization.Serializable

@Serializable
data class Like(
    val userId: String,
    val parentId: String,
    val parentType: Int
){
    companion object{

        fun fromLikeEntity(likeEntity: LikeEntity): Like{
            return Like(
                userId = likeEntity.userId,
                parentId = likeEntity.parentId,
                parentType = likeEntity.parentType
            )
        }
    }
}
