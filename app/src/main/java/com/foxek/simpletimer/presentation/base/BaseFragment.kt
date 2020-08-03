package com.foxek.simpletimer.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.foxek.simpletimer.AndroidApplication
import com.foxek.simpletimer.di.component.FragmentComponent
import com.foxek.simpletimer.di.component.DaggerFragmentComponent
import com.foxek.simpletimer.di.module.FragmentModule
import com.foxek.simpletimer.common.extensions.customTag
import java.lang.IllegalArgumentException
import javax.inject.Inject

abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> :
    Fragment(),
    BaseContract.View {

    abstract val layoutId: Int

    @Inject
    lateinit var presenter: P

    var component: FragmentComponent? = null
        private set

    abstract fun onInject(component: FragmentComponent)

    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context)

        component = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .applicationComponent(AndroidApplication.component)
            .build()

        component ?: throw IllegalArgumentException("Dagger fragment component should not be null")

        onInject(component!!)
        presenter.attachToLifecycle(lifecycle)
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(layoutId, parent, false)
    }

    @CallSuper
    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.attachView(this as V)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        attachListeners()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        presenter.detachFromLifecycle(lifecycle)
    }

    protected open fun attachListeners() {}

    override fun activityFinish() {
        activity?.finish()
    }

    override fun onBackPressed(){
        close()
    }

    override fun returnToMainScreen() {
        (activity as? BaseActivity)?.returnToMainScreen()
    }

    fun showDialog(dialog: BaseDialog){
        dialog.setTargetFragment(this,123)
        dialog.show(fragmentManager!!, dialog.customTag())
    }

    fun close() = fragmentManager?.popBackStack()
}
