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

public class IntervalCreateDialog extends BaseDialog<IntervalContact.Presenter> implements IntervalContact.DialogView{

    private Unbinder mBinder;

    @BindView(R.id.delete_button)
    TextView mDeleteButton;

    @BindView(R.id.dialog_title)
    TextView mDialogTitle;

    @BindView(R.id.work_minute_text)
    EditText mWorkMinuteText;

    @BindView(R.id.work_second_text)
    EditText mWorkSecondText;

    @BindView(R.id.rest_minute_text)
    EditText mRestMinuteText;

    @BindView(R.id.rest_second_text)
    EditText mRestSecondText;

    public static IntervalCreateDialog newInstance() {
        return new IntervalCreateDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View dialogView = inflater.inflate(R.layout.dialog_edit_interval, container, false);
        mBinder = ButterKnife.bind(this, dialogView);

        mDeleteButton.setVisibility(View.GONE);
        mDialogTitle.setText(R.string.dialog_interval_create_title);

        prepareEditText();
        getPresenter().attachDialog(this);
        getDialog().setCanceledOnTouchOutside(true);

        return dialogView;
    }

    private void prepareEditText(){
        mWorkMinuteText.setText(formatEditTextData(0));
        mWorkSecondText.setText(formatEditTextData(0));

        mRestMinuteText.setText(formatEditTextData(0));
        mRestSecondText.setText(formatEditTextData(0));
    }

    private void repairMemoryLeak(){
        mWorkMinuteText.setCursorVisible(false);
        mWorkSecondText.setCursorVisible(false);

        mRestMinuteText.setCursorVisible(false);
        mRestSecondText.setCursorVisible(false);
    }

    @OnClick(R.id.save_button)
    public void onSaveButtonClick(){
        int work_time = convertToSeconds(mWorkMinuteText.getText().toString(), mWorkSecondText.getText().toString());
        if (work_time == 0) work_time = 1;

        int rest_time = convertToSeconds(mRestMinuteText.getText().toString(), mRestSecondText.getText().toString());
        if (rest_time == 0) rest_time = 1;

        getPresenter().onIntervalCreated(work_time, rest_time);
        repairMemoryLeak();
        dismiss();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        repairMemoryLeak();
        mBinder.unbind();
        getPresenter().detachDialog();
    }
}