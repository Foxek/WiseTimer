package com.foxek.simpletimer.ui.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.foxek.simpletimer.ui.base.BaseView;
import com.foxek.simpletimer.ui.interval.IntervalActivity;
import com.foxek.simpletimer.R;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkoutActivity extends BaseView implements WorkoutContact.View,View.OnClickListener{

    @BindView(R.id.workout_list)
    RecyclerView mWorkoutList;

    @Inject
    WorkoutPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);

        mPresenter.attachView(this);
        mPresenter.viewIsReady();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void startIntervalActivity(int position, String name) {
        Intent intent = new Intent(this, IntervalActivity.class);
        intent.putExtra("workout_id", position);
        intent.putExtra("workout_name", name);
        startActivity(intent);
    }

    @Override
    public void setWorkoutList(WorkoutAdapter adapter) {
        mWorkoutList.setLayoutManager(new LinearLayoutManager(this));
        mWorkoutList.setAdapter(adapter);
        mWorkoutList.setNestedScrollingEnabled(false);
    }

    @OnClick({R.id.add_workout_button})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_workout_button:
//                WorkoutDialog mWorkoutDialog = WorkoutDialog.newInstance();
//                mPresenter.setDialogPresenter(mWorkoutDialog);
//                mWorkoutDialog.show(getSupportFragmentManager(), "workout_dialog");
                break;
        }
    }
}
