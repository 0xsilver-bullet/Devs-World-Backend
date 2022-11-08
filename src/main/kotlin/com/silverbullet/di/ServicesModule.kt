package com.silverbullet.di

import com.silverbullet.security.hashing.HashingService
import com.silverbullet.security.hashing.SHA256HashingService
import com.silverbullet.security.token.TokenService
import com.silverbullet.security.token.TokenServiceImpl
import org.koin.dsl.module

val servicesModule = module {

    single<TokenService> {
        TokenServiceImpl()
    }

    single<HashingService> {
        SHA256HashingService()
    }

}