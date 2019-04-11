package com.foxek.simpletimer.ui.interval.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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

import static com.foxek.simpletimer.utils.IntervalUtils.convertToSeconds;
import static com.foxek.simpletimer.utils.IntervalUtils.formatEditTextData;

public class IntervalCreateDialog extends BaseFragment {

    @Inject
    IntervalContact.Presenter presenter;

    private Unbinder binder;

    @BindView(R.id.delete_button)
    TextView deleteButton;

    @BindView(R.id.dialog_title)
    TextView dialogTitle;

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

    @BindView(R.id.repeat_name)
    TextView repeatName;

    @BindView(R.id.repeat_checkBox)
    CheckBox checkBox;

    public static IntervalCreateDialog newInstance() {
        return new IntervalCreateDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_edit_interval, container, false);

        ActivityComponent component = getActivityComponent();

        if (component != null) {
            component.inject(this);
            binder = ButterKnife.bind(this, dialogView);
        }

        deleteButton.setVisibility(View.GONE);
        dialogTitle.setText(R.string.dialog_interval_create_title);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                repeatName.setVisibility(View.VISIBLE);
                repeatText.setVisibility(View.VISIBLE);
            }else{
                repeatName.setVisibility(View.GONE);
                repeatText.setVisibility(View.GONE);
            }
        });

        prepareEditText();

        getDialog().setCanceledOnTouchOutside(true);

        return dialogView;
    }

    private void prepareEditText() {
        workMinuteText.setText(formatEditTextData(0));
        workSecondText.setText(formatEditTextData(0));

        restMinuteText.setText(formatEditTextData(0));
        restSecondText.setText(formatEditTextData(0));

        repeatText.setText("1");
    }

    private void repairMemoryLeak() {
        workMinuteText.setCursorVisible(false);
        workSecondText.setCursorVisible(false);

        restMinuteText.setCursorVisible(false);
        restSecondText.setCursorVisible(false);

        repeatText.setCursorVisible(false);
    }

    @OnClick(R.id.save_button)
    void onSaveButtonClick() {
        int workTime, restTime, repeat;

        if (!workMinuteText.getText().toString().equals("") && !workSecondText.getText().toString().equals("")) {
            workTime = convertToSeconds(workMinuteText.getText().toString(), workSecondText.getText().toString());
            if (workTime == 0) workTime = 1;
        } else
            workTime = 1;


        if (!restMinuteText.getText().toString().equals("") && !restSecondText.getText().toString().equals("")) {
            restTime = convertToSeconds(restMinuteText.getText().toString(), restSecondText.getText().toString());
            if (restTime == 0) restTime = 1;
        } else
            restTime = 1;

        if (!repeatText.getText().toString().equals("")) {
            repeat = Integer.valueOf(repeatText.getText().toString());
            if (repeat == 0) repeat = 1;
        } else
            repeat = 1;

        for (int i=1; i<=repeat; i++)
            presenter.createIntervalButtonClicked(workTime, restTime);

        repairMemoryLeak();
        dismiss();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        repairMemoryLeak();
        binder.unbind();
    }
}