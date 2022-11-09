package com.silverbullet.feature_post.data.repository

import com.silverbullet.feature_post.data.PostRepository
import com.silverbullet.feature_post.data.entity.PostEntity
import com.silverbullet.feature_post.data.entity.toPost
import com.silverbullet.feature_post.data.model.Post
import com.silverbullet.feature_user.data.entity.UserEntity
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class PostRepositoryImpl(db: CoroutineDatabase) : PostRepository {

    private val usersCollection = db.getCollection<UserEntity>("Users")
    private val postsCollection = db.getCollection<PostEntity>("Posts")
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

    override suspend fun getAllPosts(userId: String): List<Post> {
        return postsCollection
            .find(PostEntity::userId eq userId)
            .toList()
            .map { postEntity ->
                postEntity.toPost()
            }
    }
}