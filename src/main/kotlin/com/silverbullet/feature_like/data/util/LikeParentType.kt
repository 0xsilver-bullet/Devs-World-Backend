package com.silverbullet.feature_like.data.util

sealed class LikeParentType(val type: Int) {

    object Post : LikeParentType(1)

    object Comment : LikeParentType(2)

    object None : LikeParentType(-1)

    companion object {

        fun parse(type: Int): LikeParentType {
            return when (type) {
                1 -> Post
                2 -> Comment
                else -> None
            }
        }
    }
}
