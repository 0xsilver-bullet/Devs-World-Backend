package com.silverbullet.feature_user.data.repository

import com.silverbullet.feature_user.data.UserRepository
import com.silverbullet.feature_user.data.entity.UserEntity
import com.silverbullet.security.hashing.HashingService
import com.silverbullet.security.hashing.SaltedHash
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserRepositoryImpl(
    db: CoroutineDatabase,
    private val hashingService: HashingService
) : UserRepository {

    private val usersCollection = db.getCollection<UserEntity>(collectionName = "Users")

    override suspend fun createUser(
        username: String,
        email: String,
        password: String
    ): UserRepository.CreateUserResult {
        // ensure email doesn't exist
        val emailExists = usersCollection.findOne(UserEntity::email eq email) != null
        if (emailExists) {
            return UserRepository.CreateUserResult.EMAIL_ALREADY_EXITS
        }
        val saltedHash = hashingService.generateSaltedHash(password)
        val user = UserEntity(
            username = username,
            email = email,
            password = saltedHash.hash,
            salt = saltedHash.salt
        )
        val acknowledged = usersCollection
            .insertOne(user)
            .wasAcknowledged()
        return if (acknowledged)
            UserRepository.CreateUserResult.SUCCESS
        else
            UserRepository.CreateUserResult.FAILED
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): UserRepository.LoginResult {
        val user = usersCollection.findOne(UserEntity::email eq email)
            ?: return UserRepository.LoginResult.WrongCredentials
        val saltedHash = SaltedHash(user.password, user.salt)
        val isPasswordValid = hashingService.verify(
            password,
            saltedHash
        )
        return if (isPasswordValid)
            UserRepository.LoginResult.Success(user.id)
        else
            UserRepository.LoginResult.WrongCredentials
    }

    override suspend fun getUserById(id: String): UserEntity? {
        return usersCollection
            .findOneById(id)
    }

    override suspend fun updateUser(userId: String, updatedUser: UserEntity): Boolean {
        return usersCollection
            .updateOneById(
                id = userId,
                update = updatedUser
            )
            .wasAcknowledged()
    }
}