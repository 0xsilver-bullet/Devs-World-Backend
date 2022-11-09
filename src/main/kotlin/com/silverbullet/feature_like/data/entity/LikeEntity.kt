package com.silverbullet.feature_like.data.entity

import com.silverbullet.feature_like.data.model.Like
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class LikeEntity(
    val userId: String,
    val parentId: String,
    val parentType: Int,
    @BsonId
    val id: String = ObjectId().toString()
)

fun LikeEntity.toLike(): Like{
    return Like(
        userId = userId,
        parentId = parentId,
        parentType = parentType
    )
}
