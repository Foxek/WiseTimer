package com.foxek.simpletimer.ui.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : MvpView> : MvpPresenter<V> {

    var view: V? = null
        private set

    val disposable = CompositeDisposable()

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        disposable.dispose()
        view = null
    }

}