package com.foxek.simpletimer.common.utils

import android.app.ActivityManager
import android.content.Context

fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val services = activityManager.getRunningServices(Int.MAX_VALUE)
    for (runningServiceInfo in services) {
        if (runningServiceInfo.service.className == serviceClass.name) {
            return true
        }
    }
    return false
}