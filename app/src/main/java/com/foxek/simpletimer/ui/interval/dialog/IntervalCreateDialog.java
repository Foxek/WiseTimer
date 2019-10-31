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
import com.foxek.simpletimer.ui.base.BaseDialog;
import com.foxek.simpletimer.ui.interval.IntervalContact;

import javax.inject.Inject;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.foxek.simpletimer.utils.Constants.EMPTY;
import static com.foxek.simpletimer.utils.IntervalUtils.convertToSeconds;
import static com.foxek.simpletimer.utils.IntervalUtils.formatEditTextData;

public class IntervalCreateDialog extends BaseDialog {

    @Inject
    IntervalContact.Presenter presenter;

    private Unbinder binder;

    @BindView(R.id.delete_button)
    TextView deleteButton;

    @BindView(R.id.dialogTitle)
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
    CheckBox repeatCheckBox;

    @BindView(R.id.name_checkBox)
    CheckBox nameCheckBox;

    @BindView(R.id.name_edit_text)
    EditText nameText;

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

        repeatCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                repeatName.setVisibility(View.VISIBLE);
                repeatText.setVisibility(View.VISIBLE);
            } else {
                repeatName.setVisibility(View.GONE);
                repeatText.setVisibility(View.GONE);
            }
        });

        nameCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nameText.setText(EMPTY);
                nameText.setVisibility(View.GONE);
            } else {
                nameText.setVisibility(View.VISIBLE);
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
        nameText.setCursorVisible(false);
    }

    @OnClick(R.id.save_button)
    void onSaveButtonClick() {
        int workTime, restTime, repeat;
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

        if (!repeatText.getText().toString().equals(EMPTY)) {
            repeat = Integer.valueOf(repeatText.getText().toString());
            if (repeat == 0) repeat = 1;
        } else
            repeat = 1;

        if (!nameText.getText().toString().equals(EMPTY))
            name = nameText.getText().toString();

        for (int i = 1; i <= repeat; i++)
            presenter.createIntervalButtonClicked(name, workTime, restTime);

        repairMemoryLeak();
        dismiss();
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
        return "IntervalCreateDialog";
    }
}