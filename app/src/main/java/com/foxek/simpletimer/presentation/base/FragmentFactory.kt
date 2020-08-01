package com.foxek.simpletimer.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment

interface FragmentFactory<T : Fragment> {
    fun getInstance(bundle: Bundle? = null): T
}