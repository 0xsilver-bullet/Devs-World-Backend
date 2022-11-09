package com.silverbullet.di

import com.silverbullet.feature_follow.data.FollowingRepository
import com.silverbullet.feature_follow.data.repository.FollowingRepositoryImpl
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

}