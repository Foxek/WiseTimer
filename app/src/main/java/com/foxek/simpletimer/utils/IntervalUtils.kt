package com.foxek.simpletimer.utils

import java.util.*

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
    return if (Locale.getDefault().displayLanguage == "русский")
        cyrillicOrdinal(number)
    else
        latinOrdinal(number)
}

fun cyrillicOrdinal(number: Int): String {
    val rawString = number.toString()
    return when (rawString[rawString.length - 1]) {
        '2', '6', '7', '8' -> "$rawString-ой подход"
        '3' -> "$rawString-ий подход"
        else -> "$rawString-ый подход"
    }
}

fun latinOrdinal(number: Int): String {
    val sufixes = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
    return when (number % 100) {
        11, 12, 13 -> "${number}th interval"
        else -> "$number${sufixes[number % 10]} interval"
    }
}

