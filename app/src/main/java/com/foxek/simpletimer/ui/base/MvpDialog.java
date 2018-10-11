package com.foxek.simpletimer.ui.base;

public interface MvpDialog<P extends MvpMultiPresenter> {

    void attachPresenter(P presenter);

}