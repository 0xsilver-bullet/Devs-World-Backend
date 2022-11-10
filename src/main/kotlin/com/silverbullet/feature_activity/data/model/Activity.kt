package com.silverbullet.feature_activity.data.model

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
)