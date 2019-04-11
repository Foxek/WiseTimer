package com.foxek.simpletimer.ui.workout;

import android.content.Intent;
import android.os.Bundle;

import com.foxek.simpletimer.data.model.Workout;
import com.foxek.simpletimer.ui.base.BaseView;
import com.foxek.simpletimer.ui.interval.IntervalActivity;
import com.foxek.simpletimer.R;
import com.foxek.simpletimer.ui.workout.dialog.WorkoutCreateDialog;

import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_ID;
import static com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_NAME;
import static com.foxek.simpletimer.utils.Constants.WORKOUT_CREATE_DIALOG;

public class WorkoutActivity extends BaseView implements WorkoutContact.View, WorkoutAdapter.Callback {

    @BindView (R.id.workout_list)
    RecyclerView workoutList;

    @Inject WorkoutContact.Presenter    presenter;
    @Inject WorkoutAdapter              adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);

        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void startIntervalActivity(int position, String name) {
        Intent intent = new Intent(this, IntervalActivity.class);
        intent.putExtra(EXTRA_WORKOUT_ID, position)
              .putExtra(EXTRA_WORKOUT_NAME, name);
        startActivity(intent);
    }

    @Override
    public void setWorkoutList() {
        adapter.setCallback(this);

        workoutList.setItemAnimator(null);
        workoutList.setLayoutManager(new LinearLayoutManager(this));
        workoutList.setAdapter(adapter);
        workoutList.setNestedScrollingEnabled(false);
    }

    @Override
    public void renderWorkoutList(List<Workout> workoutList) {
        adapter.submitList(workoutList);
    }

    @Override
    public void showCreateDialog() {
        WorkoutCreateDialog
                .newInstance()
                .show(getSupportFragmentManager(), WORKOUT_CREATE_DIALOG);
    }

    @OnClick({R.id.create_workout_button})
    public void onCreateButtonClick() {
        presenter.createButtonClicked();
    }

    @Override
    public void onListItemClick(Workout workout) {
        presenter.onListItemClicked(workout);
    }

}
