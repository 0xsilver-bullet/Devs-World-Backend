package com.silverbullet.security.hashing

interface HashingService {

    fun generateSaltedHash(value: String, saltLength: Int = 32): SaltedHash

    /**
     * @return true if valid else false
     */
    fun verify(value: String, saltedHash: SaltedHash): Boolean
}