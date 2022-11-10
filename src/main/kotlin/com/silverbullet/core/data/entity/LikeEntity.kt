package com.silverbullet.core.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class LikeEntity(
    val userId: String,
    val parentId: String,
    val parentType: Int,
    @BsonId
    val id: String = ObjectId().toString()
)