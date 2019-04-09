package com.foxek.simpletimer.ui.timer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.ui.base.BaseView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimerActivity extends BaseView implements TimerContact.View,View.OnClickListener {

    @BindView(R.id.reset_button)
    ImageButton mResetButton;

    @BindView(R.id.pause_button)
    TextView mPauseButton;

    @BindView(R.id.counter_view)
    TextView mCounterText;

    @BindView(R.id.counter_type)
    TextView mCounterType;

    @BindView(R.id.counter_number)
    TextView mCounterNumber;

    @BindView(R.id.workout_name)
    TextView mWorkoutName;

    @Inject
    TimerContact.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mWorkoutName.setText(intent.getStringExtra("workout_name"));

        getActivityComponent().inject(this);
        mPresenter.attachView(this);

        mPresenter.prepareIntervals(intent.getIntExtra("workout_id",0));
        mPresenter.viewIsReady();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @OnClick({R.id.pause_button,R.id.reset_button})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pause_button:
                mPresenter.pauseButtonPressed();
                break;
            case R.id.reset_button:
                mPresenter.resetButtonPressed();
                break;
        }
    }

    @Override
    public void startWorkoutActivity() {
        finish();
    }

    @Override
    public void showPauseInterface() {
        mResetButton.setVisibility(View.VISIBLE);
        mPauseButton.setText(R.string.timer_continue_button);
    }

    @Override
    public void showPlayInterface() {
        mResetButton.setVisibility(View.GONE);
        mPauseButton.setText(R.string.timer_pause_button);
    }

    @Override
    public void showCurrentCounter(String time) {
        mCounterText.setText(time);
    }

    @Override
    public void showCounterType(int type) {
        mCounterType.setText(type);
    }


    @Override
    public void showCounterNumber(String number) {
        mCounterNumber.setText(number);
    }

}