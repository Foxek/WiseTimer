package com.foxek.simpletimer.ui.interval;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.data.model.Interval;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.foxek.simpletimer.utils.IntervalUtils.formatIntervalData;

public class IntervalAdapter extends ListAdapter<Interval, IntervalAdapter.ViewHolder> {

    private Callback        mCallback;

    public IntervalAdapter() {
        super(DIFF_CALLBACK);
    }

    public interface Callback {
        void onListItemClick(Interval item);
    }

    void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(@NonNull IntervalAdapter.ViewHolder holder, int position) {

        holder.mWorkIntervalText.setText(formatIntervalData(getItem(position).getWorkTime()));
        holder.mRestIntervalText.setText(formatIntervalData(getItem(position).getRestTime()));
    }

    @NonNull
    @Override
    public IntervalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interval_item, parent, false);
        return new IntervalAdapter.ViewHolder(view);
    }

    private static final DiffUtil.ItemCallback<Interval> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Interval>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Interval oldInterval, @NonNull Interval newInterval) {
                    return ((oldInterval.getWorkTime() == newInterval.getWorkTime()) &&
                            (oldInterval.getRestTime() == newInterval.getRestTime()));
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Interval oldInterval, @NonNull Interval newInterval) {
                    return ((oldInterval.getWorkTime() == newInterval.getWorkTime()) &&
                            (oldInterval.getRestTime() == newInterval.getRestTime()));
                }
            };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.work_interval_text)
        TextView mWorkIntervalText;

        @BindView(R.id.rest_interval_text)
        TextView mRestIntervalText;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @OnClick({R.id.interval_item})
        @Override
        public void onClick(View v) {
            mCallback.onListItemClick(getItem(getAdapterPosition()));
        }

    }
}