package com.silverbullet.feature_activity.data.model

import com.silverbullet.core.data.entity.ActivityEntity
import kotlinx.serialization.Serializable

@Serializable
data class Activity(
    val ownerId: String,
    val type: Int,
    val issuerId: String,
    val issuerName: String,
    val targetType: Int?,
    val targetId: String?,
    val timestamp: Long
){

    companion object{

        fun fromActivityEntity(activityEntity: ActivityEntity): Activity{
            return Activity(
                ownerId = activityEntity.ownerId,
                type = activityEntity.type,
                issuerId = activityEntity.issuerId,
                issuerName = activityEntity.issuerName,
                targetType = activityEntity.targetType,
                targetId = activityEntity.targetId,
                timestamp = activityEntity.timestamp
            )
        }
    }
}