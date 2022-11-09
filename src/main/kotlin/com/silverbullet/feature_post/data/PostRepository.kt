package com.silverbullet.feature_post.data

import com.silverbullet.feature_post.data.entity.PostEntity

interface PostRepository {

    suspend fun createPost(post: PostEntity): Boolean

}