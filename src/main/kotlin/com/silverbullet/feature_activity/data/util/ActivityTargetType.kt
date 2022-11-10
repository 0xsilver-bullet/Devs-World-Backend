package com.silverbullet.feature_activity.data.util

sealed class ActivityTargetType(val type: Int) {

    object Post : ActivityTargetType(1)

    object Comment : ActivityTargetType(2)
}
