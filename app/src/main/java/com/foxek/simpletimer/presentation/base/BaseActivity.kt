package com.foxek.simpletimer.presentation.base

import android.app.Activity

import android.os.Bundle
import androidx.annotation.CallSuper

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.foxek.simpletimer.R

abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun activityFinish() {
        finish()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0)
            ((supportFragmentManager.findFragmentById(R.id.container)) as BaseFragment<*, *>).onBackPressed()
        else
            super.onBackPressed()
    }
}
