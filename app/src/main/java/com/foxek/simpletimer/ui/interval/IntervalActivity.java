package com.foxek.simpletimer.ui.interval;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.ui.base.BaseView;
import com.foxek.simpletimer.ui.interval.dialog.IntervalCreateDialog;
import com.foxek.simpletimer.ui.interval.dialog.IntervalEditDialog;
import com.foxek.simpletimer.ui.interval.dialog.WorkoutEditDialog;
import com.foxek.simpletimer.ui.timer.TimerActivity;


import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntervalActivity extends BaseView implements IntervalContact.View, View.OnClickListener{

    @BindView(R.id.interval_list)
    RecyclerView mIntervalList;

    @BindView(R.id.workout_name)
    TextView mWorkoutName;

    @BindView(R.id.set_volume_button)
    ImageButton mVolumeButton;

    @Inject
    IntervalPresenter       mPresenter;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval);
        ButterKnife.bind(this);

        intent = getIntent();
        setWorkoutName(intent.getStringExtra("workout_name"));

        getActivityComponent().inject(this);

        mPresenter.attachView(this);
        mPresenter.createIntervalListAdapter(intent.getIntExtra("workout_id",0));
        mPresenter.viewIsReady();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showIntervalEditDialog(int work_time,int rest_time) {
//        IntervalEditDialog mIntervalEditDialog = IntervalEditDialog.newInstance(work_time,rest_time);
//        mPresenter.setDialogPresenter(mIntervalEditDialog);
//        mIntervalEditDialog.show(getSupportFragmentManager(), "interval_edit_dialog");
    }

    @Override
    public void showIntervalCreateDialog() {
//        IntervalCreateDialog mIntervalCreateDialog = IntervalCreateDialog.newInstance();
//        mPresenter.setDialogPresenter(mIntervalCreateDialog);
//        mIntervalCreateDialog.show(getSupportFragmentManager(), "interval_create_dialog");
    }

    @Override
    public void showWorkoutEditDialog() {
//        WorkoutEditDialog mWorkoutEditDialog = WorkoutEditDialog.newInstance(intent.getStringExtra("workout_name"));
//        mPresenter.setDialogPresenter(mWorkoutEditDialog);
//        mWorkoutEditDialog.show(getSupportFragmentManager(), "workout_edit_dialog");
    }

    @Override
    public void setWorkoutName(String name) {
        mWorkoutName.setText(name);
    }

    @Override
    public void setIntervalList(IntervalAdapter adapter) {
        mIntervalList.setLayoutManager(new LinearLayoutManager(this));
        mIntervalList.setAdapter(adapter);
    }

    @Override
    public void setVolumeState(int state) {
        if (state == 1)
            mVolumeButton.setImageResource(R.drawable.ic_menu_volume_on_white);
        else
            mVolumeButton.setImageResource(R.drawable.ic_menu_volume_off_white);
    }

    @Override
    public void startWorkoutActivity() {
        onBackPressed();
    }

    @Override
    public void startTimerActivity() {
        Intent TimerIntent = new Intent(this, TimerActivity.class);
        TimerIntent.putExtra("workout_id", intent.getIntExtra("workout_id",0));
        TimerIntent.putExtra("workout_name", intent.getStringExtra("workout_name"));
        startActivity(TimerIntent);
        finish();
    }

    @OnClick({R.id.back_button,R.id.edit_button,R.id.add_interval_button,
              R.id.start_workout_button, R.id.set_volume_button})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                onBackPressed();
                break;
            case R.id.edit_button:
                mPresenter.editButtonPressed();
                break;
            case R.id.set_volume_button:
                mPresenter.setVolumeButtonPressed();
                break;
            case R.id.add_interval_button:
                mPresenter.addIntervalButtonPressed();
                break;
            case R.id.start_workout_button:
                mPresenter.startWorkout();
                break;
        }
    }
}
