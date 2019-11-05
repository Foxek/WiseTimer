package com.foxek.simpletimer.utils

fun convertToSeconds(minute: String, seconds: String): Int {
    val result: Int

    return if ((minute.isNotEmpty()) and (seconds.isNotEmpty())) {
        result = minute.toInt() * 60 + seconds.toInt()
        if (result == 0) 1 else result
    } else
        1
}

fun formatEditTextData(time: Int): String {
    return if (time < 10)
        "0$time"
    else
        time.toString()
}

fun formatIntervalData(time: Int): String {
    val minutes: Int = time / 60
    val seconds: Int = time % 60

    return if (minutes < 10 && seconds < 10) {
        if (minutes == 0 && seconds < 0)
            "00" + ":" + "00"
        else
            "0$minutes:0$seconds"
    } else if (minutes < 10 && seconds >= 10)
        "0$minutes:$seconds"
    else if (minutes >= 10 && seconds < 10)
        "$minutes:0$seconds"
    else
        "$minutes:$seconds"
}

fun formatIntervalNumber(number: Int): String {
    val rawString = number.toString()
    return when (rawString[rawString.length - 1]) {
        '2', '6', '7', '8' -> "$rawString-ой подход"
        '3' -> "$rawString-ий подход"
        else -> "$rawString-ый подход"
    }
}

