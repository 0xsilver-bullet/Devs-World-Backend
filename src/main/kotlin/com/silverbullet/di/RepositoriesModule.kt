package com.silverbullet.di

import com.silverbullet.feature_user.data.UserRepository
import com.silverbullet.feature_user.data.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {

    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }

}