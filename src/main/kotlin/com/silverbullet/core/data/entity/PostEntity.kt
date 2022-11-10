package com.silverbullet.core.data.entity

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