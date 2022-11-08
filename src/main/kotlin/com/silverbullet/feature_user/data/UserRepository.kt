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

    suspend fun loginUser(
        email: String,
        password: String
    ): LoginResult

    enum class CreateUserResult {
        SUCCESS,
        EMAIL_ALREADY_EXITS,
        FAILED
    }

    sealed class LoginResult{
        class Success(val userId: String): LoginResult()

        object WrongCredentials: LoginResult()
    }

}