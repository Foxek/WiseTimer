package com.foxek.simpletimer.ui.base;

import android.support.v4.app.DialogFragment;

public abstract class BaseDialog<T extends  MvpMultiPresenter> extends DialogFragment implements MvpDialog<T> {

    private T presenter;

    @Override
    public void attachPresenter(T mvpPresenter) {
        presenter = mvpPresenter;
    }

    protected T getPresenter(){
        return presenter;
    }


}
