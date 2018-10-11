package com.foxek.simpletimer.ui.base;

public interface MvpMultiPresenter <V extends MvpView, D extends MvpDialog> {

    void attachView(V mvpView);

    void attachDialog(D mvpDialog);

    void viewIsReady();

    void DialogIsReady();

    void detachView();

    void detachDialog();
}
