package com.silverbullet.feature_post.data.repository

import com.silverbullet.feature_post.data.PostRepository
import com.silverbullet.feature_post.data.entity.PostEntity
import org.litote.kmongo.coroutine.CoroutineDatabase

class PostRepositoryImpl(db: CoroutineDatabase) : PostRepository {

    val postsCollection = db.getCollection<PostEntity>("Posts")

    override suspend fun createPost(post: PostEntity): Boolean {
        return postsCollection
            .insertOne(post)
            .wasAcknowledged()
    }
}