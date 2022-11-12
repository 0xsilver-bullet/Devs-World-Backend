package com.silverbullet.core.data.interfaces

import com.silverbullet.core.data.entity.PostEntity

interface PostRepository {

    suspend fun createPost(post: PostEntity, userPostsCount: Int): Boolean

    suspend fun getAllPosts(
        userId: String,
        page: Int,
        offset: Int
    ): List<PostEntity>

}