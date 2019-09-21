package com.foxek.simpletimer.ui.interval.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.di.component.ActivityComponent;
import com.foxek.simpletimer.ui.base.BaseFragment;
import com.foxek.simpletimer.ui.interval.IntervalContact;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_NAME;

public class WorkoutEditDialog extends BaseFragment {

    @Inject
    IntervalContact.Presenter presenter;

    private Unbinder binder;

    @BindView(R.id.training_edit_text)
    EditText workoutEditText;

    public static WorkoutEditDialog newInstance(String workoutName) {
        WorkoutEditDialog mWorkoutEditDialog = new WorkoutEditDialog();
        Bundle args = new Bundle();
        args.putString(EXTRA_WORKOUT_NAME, workoutName);
        mWorkoutEditDialog.setArguments(args);
        return mWorkoutEditDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_edit_workout, container, false);

        ActivityComponent component = getActivityComponent();

        if (component != null) {
            component.inject(this);
            binder = ButterKnife.bind(this, dialogView);
        }

        workoutEditText.setText(getArguments().getString(EXTRA_WORKOUT_NAME));
        workoutEditText.setSelection(workoutEditText.getText().length());

        getDialog().setCanceledOnTouchOutside(true);

        return dialogView;
    }

    @OnClick(R.id.delete_button)
    void onDeleteButtonClick() {
        if (getShowsDialog()) {
            workoutEditText.setCursorVisible(false);
            getDialog().cancel();
            presenter.deleteWorkoutButtonClicked();
        } else
            dismiss();
    }

    @OnClick(R.id.save_button)
    void onSaveButtonClick() {
        if (!workoutEditText.getText().toString().isEmpty()) {
            workoutEditText.setCursorVisible(false);
            dismiss();
            presenter.saveWorkoutButtonClicked(workoutEditText.getText().toString());
        } else
            dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        workoutEditText.setCursorVisible(false);
        binder.unbind();
    }
}