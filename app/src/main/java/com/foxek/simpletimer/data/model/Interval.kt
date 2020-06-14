package com.foxek.simpletimer.data.model

data class Interval(
    var value: Int,
    var type: Int,
    var position: Int,
    var name: String?,
    var nextName: String?
)