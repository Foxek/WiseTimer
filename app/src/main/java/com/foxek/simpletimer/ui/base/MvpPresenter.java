package com.foxek.simpletimer.ui.base;

public interface MvpPresenter<V extends MvpView, I extends MvpInteractor> {

    void attachView(V view);

    void viewIsReady();

    V getView();

    I getInteractor();

    void detachView();

}
