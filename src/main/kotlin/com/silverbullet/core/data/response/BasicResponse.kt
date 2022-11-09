package com.silverbullet.core.data.response

import kotlinx.serialization.Serializable

@Serializable
data class BasicResponse<T>(
    val successful: Boolean,
    val message: String? = null,
    val data: T? = null
)
