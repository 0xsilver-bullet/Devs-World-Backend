package com.silverbullet.core.data.repository

import com.silverbullet.core.data.entity.ActivityEntity
import com.silverbullet.core.data.interfaces.ActivityRepository
import com.silverbullet.feature_activity.data.model.Activity
import com.silverbullet.utils.CollectionNames
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class ActivityRepositoryImpl(db: CoroutineDatabase) : ActivityRepository {

    private val activitiesCollection = db.getCollection<ActivityEntity>(CollectionNames.ACTIVITIES_COLLECTION)

    override suspend fun getActivitiesForUser(userId: String): List<Activity> {
        return activitiesCollection
            .find(ActivityEntity::ownerId eq userId)
            .toList()
            .map { Activity.fromActivityEntity(it) }
    }

}