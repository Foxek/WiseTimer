package com.foxek.simpletimer.ui.base;

public abstract class BaseMultiPresenter <V extends MvpView,D extends MvpDialog> implements MvpMultiPresenter<V,D> {

    private V view;
    private D dialog;

    @Override
    public void attachView(V mvpView) {
        view = mvpView;
    }

    @Override
    public void attachDialog(D mvpDialog) {
        dialog = mvpDialog;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void detachDialog() {
        dialog = null;
    }

    protected V getView() {
        return view;
    }

    public D getDialog() {
        return dialog;
    }

    public void setDialogPresenter(MvpDialog dialog){
        dialog.attachPresenter(this);
    }
}