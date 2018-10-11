package com.foxek.simpletimer.ui.base;

public interface MvpPresenter <V extends MvpView> {

    void attachView(V mvpView);

    void viewIsReady();

    void detachView();

}
