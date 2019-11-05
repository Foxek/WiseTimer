package com.foxek.simpletimer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.foxek.simpletimer.R
import com.foxek.simpletimer.di.component.ActivityComponent

abstract class BaseDialog : DialogFragment(), MvpView {

    abstract val layoutId: Int
    abstract val dialogTag: String

    val activityComponent: ActivityComponent?
        get() = (targetFragment as BaseFragment).component

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, bundle: Bundle?): View? {
        dialog?.setCanceledOnTouchOutside(true)
        return inflater.inflate(layoutId, parent, false)
    }

    open fun repairMemoryLeak(vararg widgets: EditText) {
        for (et in widgets)
            et.isCursorVisible = false
    }

    fun checkNotEmpty(vararg widgets: EditText): Boolean {
        return widgets.all { it.text.isNotEmpty() }
    }

}