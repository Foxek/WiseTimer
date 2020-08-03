package com.foxek.simpletimer.presentation.base

interface MvpPresenter<V : MvpView> {
    fun attachView(view: V)
    fun detachView()
}
