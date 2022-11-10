package com.silverbullet.di

import com.silverbullet.core.data.interfaces.ActivityRepository
import com.silverbullet.core.data.repository.ActivityRepositoryImpl
import com.silverbullet.core.data.interfaces.CommentsRepository
import com.silverbullet.core.data.repository.CommentsRepositoryImpl
import com.silverbullet.core.data.interfaces.FollowingRepository
import com.silverbullet.core.data.repository.FollowingRepositoryImpl
import com.silverbullet.core.data.interfaces.LikesRepository
import com.silverbullet.core.data.repository.LikesRepositoryImpl
import com.silverbullet.core.data.interfaces.PostRepository
import com.silverbullet.core.data.repository.PostRepositoryImpl
import com.silverbullet.core.data.interfaces.UserRepository
import com.silverbullet.core.data.repository.UserRepositoryImpl
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

    single<ActivityRepository>{
        ActivityRepositoryImpl(get())
    }
}