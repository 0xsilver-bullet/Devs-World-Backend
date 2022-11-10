package com.silverbullet.core.data.interfaces

import com.silverbullet.core.data.entity.PostEntity
import com.silverbullet.feature_post.data.model.Post

interface PostRepository {

    suspend fun createPost(post: PostEntity, userPostsCount: Int): Boolean

    suspend fun getAllPosts(userId: String): List<Post>

}