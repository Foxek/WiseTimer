package com.foxek.simpletimer.ui.base

interface MvpPresenter<V : MvpView> {

    fun attachView(view: V)

    fun viewIsReady()

    fun detachView()

}
