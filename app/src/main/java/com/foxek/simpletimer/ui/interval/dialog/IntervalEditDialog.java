package com.foxek.simpletimer.ui.interval.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.di.component.ActivityComponent;
import com.foxek.simpletimer.ui.base.BaseDialog;
import com.foxek.simpletimer.ui.interval.IntervalContact;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.foxek.simpletimer.utils.Constants.EMPTY;
import static com.foxek.simpletimer.utils.Constants.EXTRA_INTERVAL_NAME;
import static com.foxek.simpletimer.utils.Constants.EXTRA_REST_TIME;
import static com.foxek.simpletimer.utils.Constants.EXTRA_WORK_TIME;
import static com.foxek.simpletimer.utils.IntervalUtils.convertToSeconds;
import static com.foxek.simpletimer.utils.IntervalUtils.formatEditTextData;

public class IntervalEditDialog extends BaseDialog {

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

    @BindView(R.id.repeats_group)
    Group repeatGroup;

    @BindView(R.id.name_checkBox)
    CheckBox nameCheckBox;

    @BindView(R.id.name_edit_text)
    EditText nameText;

    public static IntervalEditDialog newInstance(String name, int work_time, int rest_time) {
        IntervalEditDialog mIntervalEditDialog = new IntervalEditDialog();
        Bundle args = new Bundle();
        args.putInt(EXTRA_WORK_TIME, work_time);
        args.putInt(EXTRA_REST_TIME, rest_time);
        args.putString(EXTRA_INTERVAL_NAME, name);
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

        prepareEditText(getArguments().getString(EXTRA_INTERVAL_NAME),
                getArguments().getInt(EXTRA_WORK_TIME),
                getArguments().getInt(EXTRA_REST_TIME));

        nameCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nameText.setText(EMPTY);
                nameText.setVisibility(View.GONE);
            } else {
                nameText.setVisibility(View.VISIBLE);
            }
        });

        repeatGroup.setVisibility(View.GONE);

        getDialog().setCanceledOnTouchOutside(true);

        return dialogView;
    }

    private void prepareEditText(String name, int work_time, int rest_time) {

        if (!name.equals(EMPTY)) {
            nameCheckBox.setChecked(false);
            nameText.setVisibility(View.VISIBLE);
            nameText.setText(name);
        }

        workMinuteText.setText(formatEditTextData(work_time / 60));
        workSecondText.setText(formatEditTextData(work_time % 60));

        restMinuteText.setText(formatEditTextData(rest_time / 60));
        restSecondText.setText(formatEditTextData(rest_time % 60));
    }

    private void repairMemoryLeak() {
        workMinuteText.setCursorVisible(false);
        workSecondText.setCursorVisible(false);

        restMinuteText.setCursorVisible(false);
        restSecondText.setCursorVisible(false);

        repeatText.setCursorVisible(false);
        nameText.setCursorVisible(false);
    }

    @OnClick(R.id.save_button)
    void onSaveButtonClick() {
        int workTime, restTime;
        String name = EMPTY;

        if (!workMinuteText.getText().toString().equals(EMPTY) && !workSecondText.getText().toString().equals(EMPTY)) {
            workTime = convertToSeconds(workMinuteText.getText().toString(), workSecondText.getText().toString());
            if (workTime == 0) workTime = 1;
        } else
            workTime = 1;


        if (!restMinuteText.getText().toString().equals(EMPTY) && !restSecondText.getText().toString().equals(EMPTY)) {
            restTime = convertToSeconds(restMinuteText.getText().toString(), restSecondText.getText().toString());
            if (restTime == 0) restTime = 1;
        } else
            restTime = 1;

        if (!nameText.getText().toString().equals(EMPTY))
            name = nameText.getText().toString();

        presenter.saveIntervalButtonClicked(name, workTime, restTime);
        repairMemoryLeak();
        dismiss();
    }

    @OnClick(R.id.delete_button)
    void onDeleteButtonClick() {
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

    @NotNull
    @Override
    public String getDialogTag() {
        return "IntervalEditDialog";
    }
}