package com.silverbullet.feature_activity.service

import com.silverbullet.core.data.interfaces.ActivityRepository
import com.silverbullet.feature_activity.data.model.Activity

class ActivityService(private val repository: ActivityRepository) {

    suspend fun getUserActivities(userId: String): List<Activity> {
        return repository.getActivitiesForUser(userId)
    }
}