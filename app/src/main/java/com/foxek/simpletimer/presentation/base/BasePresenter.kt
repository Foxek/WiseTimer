package com.foxek.simpletimer.presentation.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V>, LifecycleObserver {

    var view: V? = null
        private set

    private val disposables = CompositeDisposable()

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        onViewResumed()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        disposables.clear()
    }

    override fun attachToLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    override fun detachFromLifecycle(lifecycle: Lifecycle) {
        lifecycle.removeObserver(this)
    }

    protected open fun onViewResumed() {

    }

    protected fun Disposable.disposeOnPause() {
        disposables.add(this)
    }
}