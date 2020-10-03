package com.foxek.simpletimer.presentation.base

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class StringProvider @Inject constructor(private val context: Context) {

    fun getString(@StringRes stringId: Int): String {
        return context.getString(stringId)
    }

    fun getString(@StringRes stringId: Int, vararg formatArgs: Any): String {
        return context.getString(stringId, *formatArgs)
    }
}