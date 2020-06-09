package com.foxek.simpletimer.presentation.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : MvpView> : MvpPresenter<V> {

    var view: V? = null
        private set

    val disposables = CompositeDisposable()

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        disposables.dispose()
        view = null
    }

    protected fun Disposable.disposeOnDestroy() {
        disposables.add(this)
    }
}