package com.foxek.simpletimer.ui.workout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.data.model.workout.Workout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    private List<Workout>                  mWorkout;
    private Context                         mContext;

    private final PublishSubject<Workout> onClickSubject = PublishSubject.create();

    WorkoutAdapter(List<Workout> dataset) {
        mWorkout = dataset;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutAdapter.ViewHolder holder, int position) {
        holder.mWorkoutName.setText(mWorkout.get(position).training_name);
        holder.mWorkoutDescription.setText(mContext.getString(R.string.number_of_intervals_text,
                mWorkout.get(position).intervalNumber));
    }

    @Override
    public int getItemCount() {
        return mWorkout.size();
    }

    void addWorkout(Workout workout) {
        mWorkout.add(workout);
        notifyDataSetChanged();
    }

    void addAllWorkouts(List<Workout> workouts) {
        mWorkout.clear();
        mWorkout.addAll(workouts);
        notifyDataSetChanged();
    }

    Observable<Workout> getPositionClicks(){
        return onClickSubject;
    }

    Workout getWorkout(int position) {
        return mWorkout.get(position);
    }

    @NonNull
    @Override
    public WorkoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        return new WorkoutAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.workout_name)
        TextView mWorkoutName;

        @BindView(R.id.workout_description)
        TextView mWorkoutDescription;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mContext = v.getContext();
        }

        @OnClick({R.id.workout_item})
        @Override
        public void onClick(View v) {
            onClickSubject.onNext(mWorkout.get(getAdapterPosition()));
        }
    }
}
