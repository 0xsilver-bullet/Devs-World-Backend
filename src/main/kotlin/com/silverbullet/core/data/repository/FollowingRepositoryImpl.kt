package com.silverbullet.core.data.repository

import com.silverbullet.core.data.entity.ActivityEntity
import com.silverbullet.feature_activity.data.util.ActivityType
import com.silverbullet.core.data.interfaces.FollowingRepository
import com.silverbullet.core.data.entity.FollowingEntity
import com.silverbullet.core.data.entity.UserEntity
import com.silverbullet.utils.CollectionNames
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class FollowingRepositoryImpl(db: CoroutineDatabase) : FollowingRepository {

    private val usersCollection = db.getCollection<UserEntity>(CollectionNames.USERS_COLLECTION)
    private val followingCollection = db.getCollection<FollowingEntity>(CollectionNames.FOLLOWINGS_COLLECTION)
    private val activitiesCollection = db.getCollection<ActivityEntity>(CollectionNames.ACTIVITIES_COLLECTION)

    override suspend fun createFollowing(following: FollowingEntity): Boolean {
        val followingUser = usersCollection.findOneById(following.followingUserId)
        val followedUser = usersCollection.findOneById(following.followedUserId)
        if (followingUser == null || followedUser == null) {
            return false
        }
        val alreadyFollows = followingCollection
            .findOne(
                and(
                    FollowingEntity::followingUserId eq following.followingUserId,
                    FollowingEntity::followedUserId eq following.followedUserId
                )
            ) != null
        if (alreadyFollows) {
            return true
        }
        return followingCollection
            .insertOne(following)
            .wasAcknowledged()
            .also { successful ->
                if (!successful)
                    return@also
                usersCollection.updateOneById(
                    followingUser.id,
                    setValue(UserEntity::followingCount, followingUser.followingCount + 1)
                )
                usersCollection.updateOneById(
                    followedUser.id,
                    setValue(UserEntity::followersCount, followedUser.followersCount + 1)
                )
                createFollowedActivity(
                    ownerId = followedUser.id,
                    issuerId = followingUser.id,
                    issuerName = followingUser.username
                )
            }
    }

    override suspend fun deleteFollowing(followingUserId: String, followedUserId: String): Boolean {
        val followingUser = usersCollection.findOneById(followingUserId)
        val followedUser = usersCollection.findOneById(followedUserId)
        if (followingUser == null || followedUser == null) {
            return false
        }
        val successful = followingCollection
            .deleteOne(
                and(
                    FollowingEntity::followingUserId eq followingUserId,
                    FollowingEntity::followedUserId eq followedUserId
                )
            )
            .deletedCount > 0
        if (successful) {
            usersCollection.updateOneById(
                followingUser.id,
                setValue(UserEntity::followingCount, followingUser.followingCount - 1)
            )
            usersCollection.updateOneById(
                followedUser.id,
                setValue(UserEntity::followersCount, followedUser.followersCount - 1)
            )
        }
        return successful
    }

    private suspend fun createFollowedActivity(
        ownerId: String,
        issuerId: String,
        issuerName: String,
    ) {
        val activityEntity = ActivityEntity(
            ownerId = ownerId,
            type = ActivityType.Followed.type,
            issuerId = issuerId,
            issuerName = issuerName,
            targetType = null,
            targetId = null,
            timestamp = System.currentTimeMillis()
        )
        activitiesCollection.insertOne(activityEntity)
    }
}