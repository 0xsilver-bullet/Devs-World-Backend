package com.silverbullet.feature_activity.data

import com.silverbullet.feature_activity.data.model.Activity

interface ActivityRepository {

    suspend fun getActivitiesForUser(userId: String): List<Activity>

}