package com.foxek.simpletimer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foxek.simpletimer.AndroidApplication
import com.foxek.simpletimer.di.component.ActivityComponent
import com.foxek.simpletimer.di.component.DaggerActivityComponent
import com.foxek.simpletimer.di.module.ActivityModule

abstract class BaseFragment : Fragment(), MvpView {

    abstract val layoutId: Int

    var component: ActivityComponent? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = DaggerActivityComponent.builder()
            .activityModule(ActivityModule())
            .applicationComponent(AndroidApplication.component)
            .build()
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(layoutId, parent, false)
    }

    inline fun executeInActivity(body: BaseActivity.() -> Unit) {
        activity?.execute(body)
    }

    open fun onBackPressed() {
        close()
    }

    fun showDialog(dialog: BaseDialog) {
        dialog.setTargetFragment(this, 123)
        dialog.show(fragmentManager!!, dialog.dialogTag)
    }

    fun close() = fragmentManager?.popBackStack()
}
