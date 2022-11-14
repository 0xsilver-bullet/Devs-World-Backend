package com.silverbullet.feature_post.service

import com.silverbullet.core.data.entity.PostEntity
import com.silverbullet.core.data.interfaces.*
import com.silverbullet.feature_post.data.model.Post
import com.silverbullet.feature_post.data.request.CreatePostRequest
import com.silverbullet.utils.Constants

class PostService(
    private val userRepository: UserRepository,
    private val postsRepository: PostRepository,
    private val likesRepository: LikesRepository,
    private val commentsRepository: CommentsRepository,
    private val followingRepository: FollowingRepository
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
        targetUserId: String,
        page: Int?,
        offset: Int?
    ): List<Post> {
        val user = userRepository.getUserById(targetUserId) ?: return emptyList()
        val postEntities = postsRepository.getAllPosts(
            userId = targetUserId,
            page = page ?: 1,
            offset = offset ?: Constants.POSTS_COUNT_PER_PAGE
        )
        return postEntities.map { postEntity ->
            val isLiked = likesRepository.isParentLikedByUser(postEntity.id, userId)
            val likesCount = likesRepository.likesCount(postEntity.id)
            val commentsCount = commentsRepository.commentsCount(postEntity.id)
            Post.fromPostEntity(
                postEntity = postEntity,
                username = user.username,
                isLiked = isLiked,
                likesCount = likesCount,
                commentsCount = commentsCount
            )
        }
    }

    suspend fun getFeed(
        userId: String,
        page: Int?,
        offset: Int?
    ): List<Post> {
        val followedByUser = followingRepository
            .getUserFollows(userId)
        val postsEntities = postsRepository.getUsersPosts(
            usersIds = followedByUser.map { it.followedUserId },
            page = page ?: 1,
            offset = offset ?: Constants.POSTS_COUNT_PER_PAGE
        )
        val feed = mutableListOf<Post>()
        for (postEntity in postsEntities) {
            val postOwner = userRepository.getUserById(postEntity.userId) ?: continue
            val likesCount = likesRepository.likesCount(postEntity.id)
            val commentsCount = commentsRepository.commentsCount(postEntity.id)
            val isLiked = likesRepository.isParentLikedByUser(postEntity.id, userId)
            val post = Post.fromPostEntity(
                postEntity = postEntity,
                username = postOwner.username,
                isLiked = isLiked,
                likesCount = likesCount,
                commentsCount = commentsCount
            )
            feed.add(post)
        }
        return feed
    }
}