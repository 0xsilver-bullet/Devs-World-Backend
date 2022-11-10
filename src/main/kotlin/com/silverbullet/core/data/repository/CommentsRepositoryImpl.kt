package com.silverbullet.core.data.repository

import com.silverbullet.core.data.entity.CommentEntity
import com.silverbullet.core.data.entity.PostEntity
import com.silverbullet.core.data.entity.UserEntity
import com.silverbullet.core.data.interfaces.CommentsRepository
import com.silverbullet.feature_comment.data.model.Comment
import com.silverbullet.utils.CollectionNames
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class CommentsRepositoryImpl(db: CoroutineDatabase) : CommentsRepository {

    private val usersCollection = db.getCollection<UserEntity>(CollectionNames.USERS_COLLECTION)
    private val postsCollection = db.getCollection<PostEntity>(CollectionNames.POSTS_COLLECTION)
    private val commentsCollection = db.getCollection<CommentEntity>(CollectionNames.COMMENTS_COLLECTION)

    override suspend fun createComment(
        text: String,
        postId: String,
        userId: String
    ): Boolean {
        val user = usersCollection.findOneById(userId) ?: return false
        postsCollection.findOneById(postId) ?: return false
        val commentEntity = CommentEntity.create(
            text = text,
            postId = postId,
            userId = userId,
            username = user.username
        )
        return commentsCollection
            .insertOne(commentEntity)
            .wasAcknowledged()
    }

    override suspend fun getComments(postId: String): List<Comment> {
        return commentsCollection
            .find(CommentEntity::postId eq postId)
            .toList()
            .map { Comment.fromCommentEntity(it) }
    }
}