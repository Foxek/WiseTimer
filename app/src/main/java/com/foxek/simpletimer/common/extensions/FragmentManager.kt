package com.foxek.simpletimer.common.extensions

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.transaction(operation: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction()
        .setCustomAnimations(
            android.R.anim.slide_in_left, android.R.anim.slide_out_right,
            android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        .operation()
        .commit()