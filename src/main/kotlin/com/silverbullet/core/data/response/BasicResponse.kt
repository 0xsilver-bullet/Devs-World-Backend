package com.silverbullet.core.data.response

import kotlinx.serialization.Serializable

@Serializable
data class BasicResponse<T>(
    val succeeded: Boolean,
    val message: String? = null,
    val data: T? = null
)
