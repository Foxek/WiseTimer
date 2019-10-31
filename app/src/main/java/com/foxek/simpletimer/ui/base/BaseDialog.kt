package com.foxek.simpletimer.ui.base

import androidx.fragment.app.DialogFragment
import com.foxek.simpletimer.di.component.ActivityComponent

abstract class BaseDialog : DialogFragment(), MvpView {

    abstract val dialogTag: String

    fun getActivityComponent(): ActivityComponent? {
        return (activity as BaseActivity).activityComponent
    }

}

//    private BaseActivity mActivity;
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof BaseActivity) {
//            mActivity = (BaseActivity) context;
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        mActivity = null;
//        super.onDetach();
//    }
//
//    public BaseActivity getBaseActivity() {
//        return mActivity;
//    }
//
