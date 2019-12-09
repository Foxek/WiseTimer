package com.foxek.simpletimer.utils

import android.app.ActivityManager
import android.content.Context

object ServiceTools {

    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services: List<ActivityManager.RunningServiceInfo> = activityManager.getRunningServices(Int.MAX_VALUE)
        for (runningServiceInfo in services) {
            if (runningServiceInfo.service.className == serviceClass.name) {
                return true
            }
        }
        return false
    }
}