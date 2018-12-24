package com.foxek.simpletimer.ui.interval.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.ui.base.BaseDialog;
import com.foxek.simpletimer.ui.interval.IntervalContact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.foxek.simpletimer.data.model.interval.IntervalUtils.convertToSeconds;
import static com.foxek.simpletimer.data.model.interval.IntervalUtils.formatEditTextData;

public class IntervalEditDialog extends BaseDialog<IntervalContact.Presenter> implements IntervalContact.DialogView{

    private Unbinder mBinder;

    @BindView(R.id.delete_button)
    TextView mDeleteButton;

    @BindView(R.id.work_minute_text)
    EditText mWorkMinuteText;

    @BindView(R.id.work_second_text)
    EditText mWorkSecondText;

    @BindView(R.id.rest_minute_text)
    EditText mRestMinuteText;

    @BindView(R.id.rest_second_text)
    EditText mRestSecondText;

    @BindView(R.id.repeats_edit_text)
    EditText mRepeatText;

    @BindView(R.id.repeat_name)
    TextView mRepeatName;

    public static IntervalEditDialog newInstance(int work_time,int rest_time) {
        IntervalEditDialog mIntervalEditDialog = new IntervalEditDialog();
        Bundle args = new Bundle();
        args.putInt("work_time", work_time);
        args.putInt("rest_time", rest_time);
        mIntervalEditDialog.setArguments(args);
        return mIntervalEditDialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View dialogView = inflater.inflate(R.layout.dialog_edit_interval, container, false);
        mBinder = ButterKnife.bind(this, dialogView);

        prepareEditText(getArguments().getInt("work_time"),getArguments().getInt("rest_time"));

        mRepeatText.setVisibility(View.GONE);
        mRepeatName.setVisibility(View.GONE);

        getPresenter().attachDialog(this);
        getDialog().setCanceledOnTouchOutside(true);

        return dialogView;
    }

    private void prepareEditText(int work_time,int rest_time){
        mWorkMinuteText.setText(formatEditTextData(work_time / 60));
        mWorkSecondText.setText(formatEditTextData(work_time % 60));

        mRestMinuteText.setText(formatEditTextData(rest_time / 60));
        mRestSecondText.setText(formatEditTextData(rest_time % 60));
    }

    private void repairMemoryLeak(){
        mWorkMinuteText.setCursorVisible(false);
        mWorkSecondText.setCursorVisible(false);

        mRestMinuteText.setCursorVisible(false);
        mRestSecondText.setCursorVisible(false);

        mRepeatText.setCursorVisible(false);
    }

    @OnClick(R.id.save_button)
    public void onSaveButtonClick(){
        int work_time, rest_time;
        if (!mWorkMinuteText.getText().toString().equals("") && !mWorkSecondText.getText().toString().equals("")) {
            work_time = convertToSeconds(mWorkMinuteText.getText().toString(), mWorkSecondText.getText().toString());
            if (work_time == 0) work_time = 1;
        }else
            work_time = 1;


        if (!mRestMinuteText.getText().toString().equals("") && !mRestSecondText.getText().toString().equals("")) {
            rest_time = convertToSeconds(mRestMinuteText.getText().toString(), mRestSecondText.getText().toString());
            if (rest_time == 0) rest_time = 1;
        }else
            rest_time = 1;


        getPresenter().onIntervalChanged(work_time, rest_time);
        repairMemoryLeak();
        dismiss();
    }

    @OnClick(R.id.delete_button)
    public void onDeleteButtonClick(){
        repairMemoryLeak();
        dismiss();
        getPresenter().onDeleteInterval();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        repairMemoryLeak();
        mBinder.unbind();
        getPresenter().detachDialog();
    }
}