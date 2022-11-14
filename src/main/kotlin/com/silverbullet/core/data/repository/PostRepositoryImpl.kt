package com.silverbullet.core.data.repository

import com.silverbullet.core.data.entity.PostEntity
import com.silverbullet.core.data.entity.UserEntity
import com.silverbullet.core.data.interfaces.PostRepository
import com.silverbullet.utils.CollectionNames
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.`in`
import org.litote.kmongo.setValue

class PostRepositoryImpl(db: CoroutineDatabase) : PostRepository {

    private val usersCollection = db.getCollection<UserEntity>(CollectionNames.USERS_COLLECTION)
    private val postsCollection = db.getCollection<PostEntity>(CollectionNames.POSTS_COLLECTION)
    override suspend fun createPost(post: PostEntity, userPostsCount: Int): Boolean {
        // userPostsCount passed from posts service which already queries the user data,
        // this is better to avoid another query to update user posts count
        return postsCollection
            .insertOne(post)
            .wasAcknowledged()
            .also { created ->
                if (created) {
                    // Increase User posts count by one
                    usersCollection.updateOneById(post.userId, setValue(UserEntity::postsCount, userPostsCount))
                }
            }
    }

    override suspend fun getAllPosts(
        userId: String,
        page: Int,
        offset: Int
    ): List<PostEntity> {
        return postsCollection
            .find(PostEntity::userId eq userId)
            .skip(skip = (page - 1) * offset)
            .limit(offset)
            .partial(true)
            .descendingSort(PostEntity::timestamp)
            .toList()
    }

    override suspend fun getUsersPosts(
        usersIds: List<String>,
        page: Int,
        offset: Int
    ): List<PostEntity> {
        return postsCollection
            .find(PostEntity::userId `in` usersIds)
            .skip(skip = (page - 1) * offset)
            .limit(offset)
            .partial(true)
            .descendingSort(PostEntity::timestamp)
            .toList()
    }

    override suspend fun getPost(postId: String): PostEntity? {
        return postsCollection.findOne(PostEntity::id eq postId)
    }
}