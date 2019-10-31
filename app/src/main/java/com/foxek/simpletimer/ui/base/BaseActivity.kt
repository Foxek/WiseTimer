package com.foxek.simpletimer.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import com.foxek.simpletimer.AndroidApplication
import com.foxek.simpletimer.di.component.ActivityComponent
import com.foxek.simpletimer.di.component.DaggerActivityComponent
import com.foxek.simpletimer.di.module.ActivityModule
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.foxek.simpletimer.R

abstract class BaseActivity : AppCompatActivity(), MvpView {


    var activityComponent: ActivityComponent? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule())
                .applicationComponent((application as AndroidApplication).getComponent())
                .build()
    }

    fun addFragment(fragment: BaseFragment){
        supportFragmentManager.transaction {
            add(R.id.container, fragment)
        }
    }

    fun replaceFragment(fragment: BaseFragment){
        supportFragmentManager.transaction {
            replace(R.id.container, fragment)
        }
    }

    fun showDialog(dialog: BaseDialog){
        dialog.show(supportFragmentManager, dialog.dialogTag)
    }

    fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

inline fun FragmentManager.transaction(operation: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().operation().commit()

inline fun Activity?.execute(body: BaseActivity.() -> Unit){
    (this as? BaseActivity)?.body()
}