package com.silverbullet.di

import com.silverbullet.core.data.interfaces.*
import com.silverbullet.core.data.repository.*
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

    single<ActivityRepository> {
        ActivityRepositoryImpl(get())
    }
}