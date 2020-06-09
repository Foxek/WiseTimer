package com.foxek.simpletimer.common.extensions

import android.os.Build

fun Any.customTag(): String {
    return "wisetimer_${
    if (javaClass.simpleName.length <= 16 || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        javaClass.simpleName
    else
        javaClass.simpleName.subSequence(0..15)
    }"
}