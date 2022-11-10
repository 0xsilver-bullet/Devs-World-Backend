package com.silverbullet.core.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class FollowingEntity(
    val followingUserId: String,
    val followedUserId: String,
    @BsonId
    val id: String = ObjectId().toString()
)