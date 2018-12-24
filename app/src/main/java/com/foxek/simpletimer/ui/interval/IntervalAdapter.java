package com.foxek.simpletimer.ui.interval;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.data.database.model.Interval;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static com.foxek.simpletimer.data.database.model.IntervalUtils.formatIntervalData;

public class IntervalAdapter extends RecyclerView.Adapter<IntervalAdapter.ViewHolder> {

    private List<Interval>                  mInterval;

    private final PublishSubject<Integer> onClickSubject = PublishSubject.create();

    IntervalAdapter(List<Interval> dataset) {
        mInterval = dataset;
    }

    @Override
    public void onBindViewHolder(@NonNull IntervalAdapter.ViewHolder holder, int position) {

        holder.mWorkIntervalText.setText(formatIntervalData(mInterval.get(position).workInterval));
        holder.mRestIntervalText.setText(formatIntervalData(mInterval.get(position).restInterval));
    }

    Observable<Integer> getPositionClicks(){
        return onClickSubject;
    }

    @Override
    public int getItemCount() {
        return mInterval.size();
    }

    void addInterval(Interval interval) {
        mInterval.add(interval);
        notifyDataSetChanged();
    }

    Interval getInterval(int position) {
        return mInterval.get(position);
    }

    void updateInterval(int position, int work_time, int rest_time) {
        Interval Interval = mInterval.get(position);
        Interval.workInterval = work_time;
        Interval.restInterval = rest_time;
        notifyDataSetChanged();
    }

    void deleteInterval(int position){
        mInterval.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IntervalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interval_item, parent, false);
        return new IntervalAdapter.ViewHolder(view);
    }

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
            onClickSubject.onNext(getAdapterPosition());
        }

    }
}
