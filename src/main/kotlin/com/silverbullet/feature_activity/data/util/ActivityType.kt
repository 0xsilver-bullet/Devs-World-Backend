package com.silverbullet.feature_activity.data.util

sealed class ActivityType(val type: Int) {

    object Liked : ActivityType(1)

    object Commented : ActivityType(2)

    object Followed : ActivityType(3)

}