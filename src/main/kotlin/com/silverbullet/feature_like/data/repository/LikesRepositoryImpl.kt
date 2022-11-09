package com.silverbullet.feature_like.data.repository

import com.silverbullet.feature_comment.data.entity.CommentEntity
import com.silverbullet.feature_like.data.LikesRepository
import com.silverbullet.feature_like.data.entity.LikeEntity
import com.silverbullet.feature_like.data.entity.toLike
import com.silverbullet.feature_like.data.model.Like
import com.silverbullet.feature_like.data.util.LikeParentType
import com.silverbullet.feature_post.data.entity.PostEntity
import com.silverbullet.feature_user.data.entity.UserEntity
import com.silverbullet.utils.CollectionNames
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class LikesRepositoryImpl(db: CoroutineDatabase) : LikesRepository {

    private val usersCollection = db.getCollection<UserEntity>(CollectionNames.USERS_COLLECTION)
    private val postsCollection = db.getCollection<PostEntity>(CollectionNames.POSTS_COLLECTION)
    private val commentsCollection = db.getCollection<CommentEntity>(CollectionNames.COMMENTS_COLLECTION)
    private val likesCollection = db.getCollection<LikeEntity>(CollectionNames.LIKES_COLLECTION)

    override suspend fun placeLike(userId: String, parentId: String, parentType: LikeParentType): Boolean {
        usersCollection.findOneById(userId) ?: return false
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
                postsCollection.findOneById(parentId) ?: return false
                val likeEntity = LikeEntity(
                    userId = userId,
                    parentId = parentId,
                    parentType = parentType.type
                )
                likesCollection.insertOne(likeEntity).wasAcknowledged()
            }

            LikeParentType.Comment -> {
                commentsCollection.findOneById(parentId) ?: return false
                val likeEntity = LikeEntity(
                    userId = userId,
                    parentId = parentId,
                    parentType = parentType.type
                )
                likesCollection.insertOne(likeEntity).wasAcknowledged()
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
            .map { it.toLike() }
    }
}