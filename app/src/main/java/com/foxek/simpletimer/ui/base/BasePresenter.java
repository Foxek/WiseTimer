package com.foxek.simpletimer.ui.base;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<V extends MvpView, I extends MvpInteractor> implements MvpPresenter<V, I> {

    private V view;
    private I interactor;

    private final CompositeDisposable disposable;

    public BasePresenter(I mvpInteractor, CompositeDisposable compositeDisposable) {
        interactor = mvpInteractor;
        disposable = compositeDisposable;
    }

    @Override
    public void attachView(V mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        disposable.dispose();
        view = null;
        interactor = null;
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public I getInteractor() {
        return interactor;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }
}