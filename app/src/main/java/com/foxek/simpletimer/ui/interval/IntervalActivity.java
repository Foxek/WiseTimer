package com.foxek.simpletimer.ui.interval;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.data.model.Interval;
import com.foxek.simpletimer.ui.base.BaseView;
import com.foxek.simpletimer.ui.interval.dialog.IntervalCreateDialog;
import com.foxek.simpletimer.ui.interval.dialog.IntervalEditDialog;
import com.foxek.simpletimer.ui.interval.dialog.WorkoutEditDialog;
import com.foxek.simpletimer.ui.timer.TimerActivity;

import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_ID;
import static com.foxek.simpletimer.utils.Constants.INTERVAL_CREATE_DIALOG;
import static com.foxek.simpletimer.utils.Constants.INTERVAL_EDIT_DIALOG;
import static com.foxek.simpletimer.utils.Constants.WORKOUT_EDIT_DIALOG;

public class IntervalActivity extends BaseView implements IntervalContact.View, IntervalAdapter.Callback{

    @BindView(R.id.interval_list)
    RecyclerView intervalList;

    @BindView(R.id.workout_name)
    TextView workoutName;

    @BindView(R.id.set_volume_button)
    ImageButton volumeButton;

    @Inject IntervalContact.Presenter   presenter;
    @Inject IntervalAdapter             adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);

        presenter.attachView(this);
        presenter.viewIsReady(getIntent().getIntExtra(EXTRA_WORKOUT_ID,0));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setWorkoutName(String name) {
        workoutName.setText(name);
    }

    @Override
    public void setIntervalList() {
        adapter.setCallback(this);

        intervalList.setItemAnimator(null);
        intervalList.setLayoutManager(new LinearLayoutManager(this));
        intervalList.setAdapter(adapter);
    }

    @Override
    public void renderIntervalList(List<Interval> intervalList) {
        adapter.submitList(intervalList);
    }

    @Override
    public void setVolumeState(boolean state) {
        if (state)
            volumeButton.setImageResource(R.drawable.ic_menu_volume_on_white);
        else
            volumeButton.setImageResource(R.drawable.ic_menu_volume_off_white);
    }

    @Override
    public void showIntervalEditDialog(int workTime, int restTime) {
        IntervalEditDialog
                .newInstance(workTime, restTime)
                .show(getSupportFragmentManager(), INTERVAL_EDIT_DIALOG);
    }

    @Override
    public void showIntervalCreateDialog() {
        IntervalCreateDialog
                .newInstance()
                .show(getSupportFragmentManager(), INTERVAL_CREATE_DIALOG);
    }

    @Override
    public void showWorkoutEditDialog() {
        WorkoutEditDialog
                .newInstance(getIntent().getStringExtra(EXTRA_WORKOUT_ID))
                .show(getSupportFragmentManager(), WORKOUT_EDIT_DIALOG);
    }
    @Override
    public void startWorkoutActivity() {
        onBackPressed();
    }

    @Override
    public void startTimerActivity() {
        Intent timerIntent = new Intent(this, TimerActivity.class);
        timerIntent
                .putExtra(EXTRA_WORKOUT_ID, getIntent().getIntExtra(EXTRA_WORKOUT_ID,0))
                .putExtra("workout_name", getIntent().getStringExtra("workout_name"));
        startActivity(timerIntent);
        finish();
    }

    @OnClick({R.id.back_button,R.id.edit_button,R.id.add_interval_button,
              R.id.start_workout_button, R.id.set_volume_button})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                onBackPressed();
                break;
            case R.id.edit_button:
                presenter.editWorkoutButtonClicked();
                break;
            case R.id.set_volume_button:
                presenter.changeVolumeButtonClicked();
                break;
            case R.id.add_interval_button:
                presenter.addIntervalButtonClicked();
                break;
            case R.id.start_workout_button:
                presenter.startWorkoutButtonClicked();
                break;
        }
    }

    @Override
    public void onListItemClick(Interval item) {
        presenter.intervalItemClicked(item);
    }
}