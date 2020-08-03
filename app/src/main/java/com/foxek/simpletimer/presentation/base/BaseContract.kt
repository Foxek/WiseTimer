package com.foxek.simpletimer.presentation.base

import androidx.lifecycle.Lifecycle

interface BaseContract {

    interface View : MvpView {
        fun onBackPressed()
        fun activityFinish()
        fun returnToMainScreen()
    }

    interface Presenter<V : View> : MvpPresenter<V> {
        fun attachToLifecycle(lifecycle: Lifecycle)
        fun detachFromLifecycle(lifecycle: Lifecycle)
    }
}