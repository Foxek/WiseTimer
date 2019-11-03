package com.foxek.simpletimer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foxek.simpletimer.di.component.ActivityComponent

abstract class BaseFragment : Fragment(), MvpView {

    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(layoutId, parent, false)
    }

    inline fun executeInActivity(body: BaseActivity.() -> Unit){
        activity?.execute(body)
    }

    open fun onBackPressed(){
        close()
    }

    fun hideSoftKeyboard() = executeInActivity { hideSoftKeyboard() }

    fun showMessage(message: String) = executeInActivity { showMessage(message) }

    fun showDialog(dialog: BaseDialog) = executeInActivity { showDialog(dialog) }

    fun close() = fragmentManager?.popBackStack()
}
