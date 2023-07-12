

@file:JvmName("ContantsRecord")

package com.example.safesite.record



@JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Verbose workmanager notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "shows notifications whenever work starts"
@JvmField val NOTIFICATION_TITLE : CharSequence = "SafeSite says"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID=1


const val DELAY_TIME_MILLIS: Long = 3000