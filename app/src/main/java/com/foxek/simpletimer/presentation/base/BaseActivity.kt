package com.foxek.simpletimer.presentation.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
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
        if (!onBackPressedConsumedByFragment())
            super.onBackPressed()
    }

    protected fun onBackPressedConsumedByFragment(): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment is BackPressedListener && fragment.onBackPressedConsumed())
            return true

        return false
    }

    override fun returnToMainScreen() {
        while (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStackImmediate()
        }
    }
}
