package com.silverbullet.di

import com.silverbullet.feature_activity.service.ActivityService
import com.silverbullet.feature_comment.service.CommentsService
import com.silverbullet.feature_follow.service.FollowingService
import com.silverbullet.feature_like.service.LikesService
import com.silverbullet.feature_post.service.PostService
import com.silverbullet.feature_search.service.SearchService
import com.silverbullet.feature_user.service.UserService
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

    single {
        UserService(get())
    }

    single {
        PostService(get(), get(),get(),get())
    }

    single {
        FollowingService(get())
    }

    single {
        LikesService(get())
    }

    single {
        CommentsService(get())
    }

    single {
        ActivityService(get())
    }

    single {
        SearchService(get())
    }
}