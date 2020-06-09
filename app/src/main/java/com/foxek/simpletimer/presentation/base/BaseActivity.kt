package com.foxek.simpletimer.presentation.base

import android.app.Activity

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.foxek.simpletimer.R

abstract class BaseActivity : AppCompatActivity(), MvpView {

    abstract var fragment: BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(fragment)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0)
            ((supportFragmentManager.findFragmentById(R.id.container)) as BaseFragment).onBackPressed()
        else
            super.onBackPressed()
    }

    private fun addFragment(fragment: BaseFragment) {
        supportFragmentManager.transaction {
            add(R.id.container, fragment)
        }
    }

    fun replaceFragment(fragment: BaseFragment, args: Bundle?) {
        fragment.arguments = args

        supportFragmentManager.transaction {
            replace(R.id.container, fragment)
            addToBackStack(fragment.tag)
        }
    }
}

inline fun FragmentManager.transaction(operation: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction()
        .setCustomAnimations(
            android.R.anim.slide_in_left, android.R.anim.slide_out_right,
            android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        .operation()
        .commit()

inline fun Activity?.execute(body: BaseActivity.() -> Unit) {
    (this as? BaseActivity)?.body()
}