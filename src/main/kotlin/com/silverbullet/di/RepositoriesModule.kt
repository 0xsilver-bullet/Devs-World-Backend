package com.silverbullet.di

import com.silverbullet.feature_comment.data.CommentsRepository
import com.silverbullet.feature_comment.data.repository.CommentsRepositoryImpl
import com.silverbullet.feature_follow.data.FollowingRepository
import com.silverbullet.feature_follow.data.repository.FollowingRepositoryImpl
import com.silverbullet.feature_like.data.LikesRepository
import com.silverbullet.feature_like.data.repository.LikesRepositoryImpl
import com.silverbullet.feature_post.data.PostRepository
import com.silverbullet.feature_post.data.repository.PostRepositoryImpl
import com.silverbullet.feature_user.data.UserRepository
import com.silverbullet.feature_user.data.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {

    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }

    single<PostRepository> {
        PostRepositoryImpl(get())
    }

    single<FollowingRepository> {
        FollowingRepositoryImpl(get())
    }

    single<LikesRepository> {
        LikesRepositoryImpl(get())
    }

    single<CommentsRepository> {
        CommentsRepositoryImpl(get())
    }

}