package com.foxek.simpletimer.ui.base;

import android.os.Bundle;

import com.foxek.simpletimer.common.MainApplication;
import com.foxek.simpletimer.di.component.ActivityComponent;
import com.foxek.simpletimer.di.component.DaggerActivityComponent;
import com.foxek.simpletimer.di.module.ActivityModule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseView extends AppCompatActivity implements MvpView {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule())
                .applicationComponent(((MainApplication) getApplication()).getComponent())
                .build();

    }

    protected ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

}

