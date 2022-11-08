package com.silverbullet.feature_user.data

interface UserRepository {

    /**
     * @return true if succeeded
     */
    suspend fun createUser(
        username: String,
        email: String,
        password: String
    ): CreateUserResult

    enum class CreateUserResult {
        SUCCESS,
        EMAIL_ALREADY_EXITS,
        FAILED
    }

}