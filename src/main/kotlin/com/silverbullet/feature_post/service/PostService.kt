package com.silverbullet.feature_post.service

import com.silverbullet.core.data.entity.PostEntity
import com.silverbullet.core.data.interfaces.CommentsRepository
import com.silverbullet.core.data.interfaces.LikesRepository
import com.silverbullet.core.data.interfaces.PostRepository
import com.silverbullet.feature_post.data.model.Post
import com.silverbullet.feature_post.data.request.CreatePostRequest
import com.silverbullet.core.data.interfaces.UserRepository
import com.silverbullet.utils.Constants

class PostService(
    private val userRepository: UserRepository,
    private val postsRepository: PostRepository,
    private val likesRepository: LikesRepository,
    private val commentsRepository: CommentsRepository
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
        return postsRepository.createPost(post, userPostsCount = user.postsCount + 1)
    }

    suspend fun getAllPosts(
        userId: String,
        page: Int?,
        offset: Int?
    ): List<Post> {
        val user = userRepository.getUserById(userId) ?: return emptyList()
        val postEntities = postsRepository.getAllPosts(
            userId = userId,
            page = page ?: 1,
            offset = offset ?: Constants.POSTS_COUNT_PER_PAGE
        )
        return postEntities.map { postEntity ->
            val likesCount = likesRepository.likesCount(postEntity.id)
            val commentsCount = commentsRepository.commentsCount(postEntity.id)
            Post.fromPostEntity(
                postEntity = postEntity,
                username = user.username,
                likesCount = likesCount,
                commentsCount = commentsCount
            )
        }
    }
}