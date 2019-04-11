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

import static com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_ID;
import static com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_NAME;

public class TimerActivity extends BaseView implements TimerContact.View,View.OnClickListener {

    @BindView(R.id.reset_button)
    ImageButton resetButton;

    @BindView(R.id.pause_button)
    TextView pauseButton;

    @BindView(R.id.counter_view)
    TextView counterText;

    @BindView(R.id.counter_type)
    TextView counterType;

    @BindView(R.id.counter_number)
    TextView counterNumber;

    @BindView(R.id.workout_name)
    TextView workoutName;

    @Inject
    TimerContact.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        workoutName.setText(intent.getStringExtra(EXTRA_WORKOUT_NAME));

        getActivityComponent().inject(this);
        presenter.attachView(this);

        presenter.prepareIntervals(intent.getIntExtra(EXTRA_WORKOUT_ID,0));
        presenter.viewIsReady();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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
                presenter.pauseButtonClicked();
                break;
            case R.id.reset_button:
                presenter.resetButtonClicked();
                break;
        }
    }

    @Override
    public void startWorkoutActivity() {
        finish();
    }

    @Override
    public void showPauseInterface() {
        resetButton.setVisibility(View.VISIBLE);
        pauseButton.setText(R.string.timer_continue_button);
    }

    @Override
    public void showPlayInterface() {
        resetButton.setVisibility(View.GONE);
        pauseButton.setText(R.string.timer_pause_button);
    }

    @Override
    public void showCurrentCounter(String time) {
        counterText.setText(time);
    }

    @Override
    public void showCounterType(int type) {
        counterType.setText(type);
    }


    @Override
    public void showCounterNumber(String number) {
        counterNumber.setText(number);
    }

}