package com.silverbullet.feature_post.service

import com.silverbullet.feature_post.data.PostRepository
import com.silverbullet.feature_post.data.entity.PostEntity
import com.silverbullet.feature_post.data.model.Post
import com.silverbullet.feature_post.data.request.CreatePostRequest
import com.silverbullet.feature_user.data.UserRepository

class PostService(
    private val userRepository: UserRepository,
    private val repository: PostRepository
) {

    suspend fun createPost(
        userId: String,
        request: CreatePostRequest,
        imageUrl: String,
        imageInternalPath: String
    ): Boolean {
        if (request.description.isBlank()) {
            return false
        }
        val user = userRepository.getUserById(userId) ?: return false
        val post = PostEntity(
            imageUrl = imageUrl,
            imageInternalPath = imageInternalPath,
            userId = userId,
            timestamp = System.currentTimeMillis(),
            description = request.description
        )
        return repository.createPost(post, userPostsCount = user.postsCount + 1)
    }

    suspend fun getAllPosts(userId: String): List<Post> {
        return repository.getAllPosts(userId)
    }
}