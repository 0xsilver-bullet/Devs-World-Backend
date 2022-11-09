package com.silverbullet.feature_post.data

import com.silverbullet.feature_post.data.entity.PostEntity
import com.silverbullet.feature_post.data.model.Post

interface PostRepository {

    suspend fun createPost(post: PostEntity, userPostsCount: Int): Boolean

    suspend fun getAllPosts(userId: String): List<Post>

}