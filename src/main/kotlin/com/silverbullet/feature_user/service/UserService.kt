package com.silverbullet.feature_user.service

import com.silverbullet.feature_user.data.UserRepository
import com.silverbullet.feature_user.data.request.CreateUserRequest
import com.silverbullet.feature_user.data.request.LoginRequest

class UserService(private val repository: UserRepository) {

    suspend fun createUser(request: CreateUserRequest): UserRepository.CreateUserResult {
        return repository.createUser(
            username = request.username,
            email = request.email,
            password = request.password
        )
    }

    suspend fun loginUser(request: LoginRequest): UserRepository.LoginResult {
        return repository.loginUser(
            email = request.email,
            password = request.password
        )
    }

}