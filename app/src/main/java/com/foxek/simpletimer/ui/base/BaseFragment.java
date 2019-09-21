package com.foxek.simpletimer.ui.base;

import android.content.Context;

import com.foxek.simpletimer.di.component.ActivityComponent;

import androidx.fragment.app.DialogFragment;

public abstract class BaseFragment extends DialogFragment implements MvpView {

    private BaseView mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseView) {
            mActivity = (BaseView) context;
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public BaseView getBaseActivity() {
        return mActivity;
    }

    public ActivityComponent getActivityComponent() {
        if (mActivity != null) {
            return mActivity.getActivityComponent();
        }
        return null;
    }
}