package com.foxek.simpletimer.ui.base;

public abstract class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    protected T getView() {
        return view;
    }

}