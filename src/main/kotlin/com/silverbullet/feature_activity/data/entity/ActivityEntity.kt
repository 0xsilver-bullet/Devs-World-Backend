package com.silverbullet.feature_activity.data.entity

import com.silverbullet.feature_activity.data.model.Activity
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

/**
 * @param ownerId the id of the user which the activity to belongs to him.
 * @param type an Int that indicates the type of Activity (Like,Comment,Follow)
 * @param issuerId the id of the user who issued the action
 * @param targetId the id of the Entity affected by the issuer action
 * @param targetType integer to indicate the type of the entity affected by action (Post , Comment, User)
 * In case targetId and targetType is null, then the affected target is the user himself.
 */
data class ActivityEntity(
    val ownerId: String,
    val type: Int,
    val issuerId: String,
    val issuerName: String,
    val targetType: Int?,
    val targetId: String?,
    val timestamp: Long,
    @BsonId
    val id: String = ObjectId().toString()
)

fun ActivityEntity.toActivity(): Activity {
    return Activity(
        ownerId = ownerId,
        type = type,
        issuerId = issuerId,
        issuerName = issuerName,
        targetType = targetType,
        targetId = targetId,
        timestamp = timestamp
    )
}
