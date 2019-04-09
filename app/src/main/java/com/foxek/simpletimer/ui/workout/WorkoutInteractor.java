package com.foxek.simpletimer.ui.workout;

import com.foxek.simpletimer.data.database.LocalDatabase;
import com.foxek.simpletimer.data.model.Interval;
import com.foxek.simpletimer.data.model.Workout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class WorkoutInteractor implements WorkoutContact.Interactor {

    private LocalDatabase   mDatabase;
    private WorkoutAdapter  mWorkoutAdapter;

    @Inject
    WorkoutInteractor(LocalDatabase database){
        mDatabase = database;
    }

    @Override
    public WorkoutAdapter createWorkoutListAdapter(){
        List<Workout> workouts = new ArrayList<>();
        mWorkoutAdapter = new WorkoutAdapter(workouts);
        return mWorkoutAdapter;
    }

    @Override
    public Disposable fetchWorkoutList(){
        return mDatabase.getWorkoutDAO().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trainings -> mWorkoutAdapter.addAllWorkouts(trainings), throwable -> {});
    }


    @Override
    public void createNewWorkout(String workoutName){
        int trainingID;

        if (mWorkoutAdapter.getItemCount() == 0)
            trainingID = 0;
        else
            trainingID = mWorkoutAdapter.getWorkout(mWorkoutAdapter.getItemCount()-1).uid + 1;

        Workout mWorkout = new Workout(workoutName,trainingID,1,1);
        mWorkoutAdapter.addWorkout(mWorkout);

        mDatabase.getWorkoutDAO().add(mWorkout);
        mDatabase.getIntervalDAO().add(new Interval(1,1,mWorkout.uid,0));
    }

    @Override
    public Observable<Workout> onWorkoutItemClick() {
        return mWorkoutAdapter.getPositionClicks();
    }
}
