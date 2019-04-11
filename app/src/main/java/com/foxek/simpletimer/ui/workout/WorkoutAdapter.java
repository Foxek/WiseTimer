package com.foxek.simpletimer.ui.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.data.model.Workout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkoutAdapter extends ListAdapter <Workout, WorkoutAdapter.ViewHolder> {

    private Context             mContext;
    private Callback            mCallback;

    @Inject
    public WorkoutAdapter() {
        super(DIFF_CALLBACK);
    }

    public interface Callback {
        void onListItemClick(Workout workout);
    }

    void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutAdapter.ViewHolder holder, int position) {
        holder.mWorkoutName.setText(getItem(position).getName());
        holder.mWorkoutDescription.setText(mContext.getString(R.string.number_of_intervals_text,
                getItem(position).getIntervalCount()));
    }

    @NonNull
    @Override
    public WorkoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        return new WorkoutAdapter.ViewHolder(view);
    }

    private static final DiffUtil.ItemCallback<Workout> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Workout>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Workout oldWorkout, @NonNull Workout newWorkout) {
                    return ((oldWorkout.getName().equals(newWorkout.getName())) &&
                            (oldWorkout.getIntervalCount() == newWorkout.getIntervalCount()) &&
                            (oldWorkout.getUid() == newWorkout.getUid()));
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Workout oldWorkout, @NonNull Workout newWorkout) {
                    return ((oldWorkout.getName().equals(newWorkout.getName())) &&
                            (oldWorkout.getIntervalCount() == newWorkout.getIntervalCount()) &&
                            (oldWorkout.getUid() == newWorkout.getUid()));
                }
            };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            mCallback.onListItemClick(getItem(getAdapterPosition()));
        }
    }
}
