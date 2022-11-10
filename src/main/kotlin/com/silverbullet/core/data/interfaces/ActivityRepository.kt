package com.silverbullet.core.data.interfaces

import com.silverbullet.feature_activity.data.model.Activity

interface ActivityRepository {

    suspend fun getActivitiesForUser(userId: String): List<Activity>

}