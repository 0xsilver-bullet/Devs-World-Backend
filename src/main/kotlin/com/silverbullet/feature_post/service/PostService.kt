package com.silverbullet.feature_post.service

import com.silverbullet.feature_post.data.PostRepository
import com.silverbullet.feature_post.data.entity.PostEntity
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
        val userExists = userRepository.getUserById(userId) != null
        if (!userExists) {
            return false
        }
        val post = PostEntity(
            imageUrl = imageUrl,
            imageInternalPath = imageInternalPath,
            userId = userId,
            timestamp = System.currentTimeMillis(),
            description = request.description
        )
        return repository.createPost(post)
    }
}