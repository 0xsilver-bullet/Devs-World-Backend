package com.silverbullet.core.data.repository

import com.silverbullet.core.data.entity.*
import com.silverbullet.feature_activity.data.util.ActivityTargetType
import com.silverbullet.feature_activity.data.util.ActivityType
import com.silverbullet.core.data.interfaces.LikesRepository
import com.silverbullet.feature_like.data.model.Like
import com.silverbullet.feature_like.data.util.LikeParentType
import com.silverbullet.utils.CollectionNames
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class LikesRepositoryImpl(db: CoroutineDatabase) : LikesRepository {

    private val usersCollection = db.getCollection<UserEntity>(CollectionNames.USERS_COLLECTION)
    private val postsCollection = db.getCollection<PostEntity>(CollectionNames.POSTS_COLLECTION)
    private val commentsCollection = db.getCollection<CommentEntity>(CollectionNames.COMMENTS_COLLECTION)
    private val likesCollection = db.getCollection<LikeEntity>(CollectionNames.LIKES_COLLECTION)
    private val activitiesCollection = db.getCollection<ActivityEntity>(CollectionNames.ACTIVITIES_COLLECTION)

    override suspend fun placeLike(userId: String, parentId: String, parentType: LikeParentType): Boolean {
        val user = usersCollection.findOneById(userId) ?: return false
        val likeAlreadyExists = likesCollection.findOne(
            and(
                LikeEntity::parentId eq parentId,
                LikeEntity::userId eq userId,
                LikeEntity::parentType eq parentType.type
            )
        ) != null
        if (likeAlreadyExists) {
            return true
        }
        return when (parentType) {
            LikeParentType.Post -> {
                val post = postsCollection.findOneById(parentId) ?: return false
                val likeEntity = LikeEntity(
                    userId = userId,
                    parentId = parentId,
                    parentType = parentType.type
                )
                likesCollection
                    .insertOne(likeEntity)
                    .wasAcknowledged()
                    .also {
                        createLikeActivity(
                            ownerId = post.userId,
                            issuerId = userId,
                            issuerName = user.username,
                            targetId = post.id,
                            targetType = ActivityTargetType.Post.type
                        )
                    }
            }

            LikeParentType.Comment -> {
                val comment = commentsCollection.findOneById(parentId) ?: return false
                val likeEntity = LikeEntity(
                    userId = userId,
                    parentId = parentId,
                    parentType = parentType.type
                )
                likesCollection
                    .insertOne(likeEntity)
                    .wasAcknowledged()
                    .also {
                        createLikeActivity(
                            ownerId = comment.userId,
                            issuerId = userId,
                            issuerName = user.username,
                            targetId = comment.id,
                            targetType = ActivityTargetType.Comment.type
                        )
                    }
            }

            LikeParentType.None -> false
        }
    }

    override suspend fun removeLike(userId: String, parentId: String, parentType: LikeParentType): Boolean {
        usersCollection.findOneById(userId) ?: return false
        val likeNotExist = likesCollection.findOne(
            and(
                LikeEntity::parentId eq parentId,
                LikeEntity::userId eq userId,
                LikeEntity::parentType eq parentType.type
            )
        ) == null
        if (likeNotExist) {
            return true
        }
        return when (parentType) {
            LikeParentType.Post -> {
                postsCollection.findOneById(parentId) ?: return false
                likesCollection.deleteOne(
                    and(
                        LikeEntity::userId eq userId,
                        LikeEntity::parentId eq parentId,
                        LikeEntity::parentType eq parentType.type
                    )
                ).deletedCount > 0
            }

            LikeParentType.Comment -> {
                commentsCollection.findOneById(parentId) ?: return false
                likesCollection.deleteOne(
                    and(
                        LikeEntity::userId eq userId,
                        LikeEntity::parentId eq parentId,
                        LikeEntity::parentType eq parentType.type
                    )
                ).deletedCount > 0
            }

            LikeParentType.None -> false
        }
    }

    override suspend fun getLikes(parentId: String): List<Like> {
        return likesCollection
            .find(LikeEntity::parentId eq parentId)
            .toList()
            .map { Like.fromLikeEntity(it) }
    }

    private suspend fun createLikeActivity(
        ownerId: String,
        issuerId: String,
        issuerName: String,
        targetType: Int,
        targetId: String
    ) {
        val activityEntity = ActivityEntity(
            ownerId = ownerId,
            type = ActivityType.Liked.type,
            issuerId = issuerId,
            issuerName = issuerName,
            targetType = targetType,
            targetId = targetId,
            timestamp = System.currentTimeMillis()
        )
        activitiesCollection.insertOne(activityEntity)
    }
}