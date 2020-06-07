package com.foxek.simpletimer.data

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit
import javax.inject.Inject

class UserPreferences @Inject constructor(val context: Context) {

    companion object {
        private const val KEY_CHANGELOG_VERSION_NAME = "KEY_CHANGELOG_VERSION_NAME"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var changelogVersion
        get() = preferences.getInt(KEY_CHANGELOG_VERSION_NAME, 0)
        set(value) {
            preferences.edit {
                putInt(KEY_CHANGELOG_VERSION_NAME, value)
            }
        }
}