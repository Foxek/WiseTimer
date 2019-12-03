package com.foxek.simpletimer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.foxek.simpletimer.di.component.ActivityComponent
import kotlinx.android.synthetic.main.dialog_edit_interval.*

abstract class BaseDialog : DialogFragment(), MvpView {

    abstract val layoutId: Int
    abstract val dialogTag: String

    val activityComponent: ActivityComponent?
        get() = (targetFragment as BaseFragment).component

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, bundle: Bundle?): View? {
        dialog?.setCanceledOnTouchOutside(true)
        return inflater.inflate(layoutId, parent, false)
    }

    fun checkNotEmpty(vararg widgets: EditText): Boolean {
        return widgets.all { it.text.isNotEmpty() }
    }

}