package com.foxek.simpletimer.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.foxek.simpletimer.common.extensions.customTag

abstract class BaseStandaloneFragment : Fragment() {

    abstract val layoutId: Int

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(layoutId, parent, false)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        attachListeners()
    }

    protected open fun attachListeners() {}

    fun activityFinish() {
        activity?.finish()
    }

    protected open fun onBackPressed() {
        close()
    }

    fun showDialog(dialog: BaseDialog) {
        dialog.setTargetFragment(this, 123)
        dialog.show(fragmentManager!!, dialog.customTag())
    }

    fun close() = fragmentManager?.popBackStack()
}