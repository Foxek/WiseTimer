package com.foxek.simpletimer.ui.interval.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.di.component.ActivityComponent;
import com.foxek.simpletimer.ui.base.BaseFragment;
import com.foxek.simpletimer.ui.interval.IntervalContact;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.foxek.simpletimer.utils.Constants.EXTRA_REST_TIME;
import static com.foxek.simpletimer.utils.Constants.EXTRA_WORK_TIME;
import static com.foxek.simpletimer.utils.IntervalUtils.convertToSeconds;
import static com.foxek.simpletimer.utils.IntervalUtils.formatEditTextData;

public class IntervalEditDialog extends BaseFragment {

    @Inject
    IntervalContact.Presenter presenter;

    private Unbinder binder;

    @BindView(R.id.work_minute_text)
    EditText workMinuteText;

    @BindView(R.id.work_second_text)
    EditText workSecondText;

    @BindView(R.id.rest_minute_text)
    EditText restMinuteText;

    @BindView(R.id.rest_second_text)
    EditText restSecondText;

    @BindView(R.id.repeats_edit_text)
    EditText repeatText;

    @BindView(R.id.repeat_checkBox)
    CheckBox checkBox;

    public static IntervalEditDialog newInstance(int work_time,int rest_time) {
        IntervalEditDialog mIntervalEditDialog = new IntervalEditDialog();
        Bundle args = new Bundle();
        args.putInt(EXTRA_WORK_TIME, work_time);
        args.putInt(EXTRA_REST_TIME, rest_time);
        mIntervalEditDialog.setArguments(args);
        return mIntervalEditDialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_edit_interval, container, false);

        ActivityComponent component = getActivityComponent();

        if (component != null) {
            component.inject(this);
            binder = ButterKnife.bind(this, dialogView);
        }

        prepareEditText(getArguments().getInt(EXTRA_WORK_TIME),getArguments().getInt(EXTRA_REST_TIME));

        repeatText.setVisibility(View.GONE);
        checkBox.setVisibility(View.GONE);

        getDialog().setCanceledOnTouchOutside(true);

        return dialogView;
    }

    private void prepareEditText(int work_time,int rest_time){
        workMinuteText.setText(formatEditTextData(work_time / 60));
        workSecondText.setText(formatEditTextData(work_time % 60));

        restMinuteText.setText(formatEditTextData(rest_time / 60));
        restSecondText.setText(formatEditTextData(rest_time % 60));
    }

    private void repairMemoryLeak(){
        workMinuteText.setCursorVisible(false);
        workSecondText.setCursorVisible(false);

        restMinuteText.setCursorVisible(false);
        restSecondText.setCursorVisible(false);

        repeatText.setCursorVisible(false);
    }

    @OnClick(R.id.save_button)
    void onSaveButtonClick(){
        int workTime, restTime;
        if (!workMinuteText.getText().toString().equals("") && !workSecondText.getText().toString().equals("")) {
            workTime = convertToSeconds(workMinuteText.getText().toString(), workSecondText.getText().toString());
            if (workTime == 0) workTime = 1;
        }else
            workTime = 1;


        if (!restMinuteText.getText().toString().equals("") && !restSecondText.getText().toString().equals("")) {
            restTime = convertToSeconds(restMinuteText.getText().toString(), restSecondText.getText().toString());
            if (restTime == 0) restTime = 1;
        }else
            restTime = 1;


        presenter.saveIntervalButtonClicked(workTime, restTime);
        repairMemoryLeak();
        dismiss();
    }

    @OnClick(R.id.delete_button)
    void onDeleteButtonClick(){
        repairMemoryLeak();
        dismiss();
        presenter.deleteIntervalButtonClicked();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        repairMemoryLeak();
        binder.unbind();
    }
}