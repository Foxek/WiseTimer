package com.foxek.simpletimer.ui.workout.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.di.component.ActivityComponent;
import com.foxek.simpletimer.ui.base.BaseFragment;
import com.foxek.simpletimer.ui.workout.WorkoutContact;

import javax.inject.Inject;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WorkoutCreateDialog extends BaseFragment {

    @Inject
    WorkoutContact.Presenter presenter;

    @BindView(R.id.error_hint)
    TextView errorHint;

    @BindView(R.id.training_edit_text)
    EditText workoutEditText;

    private Unbinder binder;

    public static WorkoutCreateDialog newInstance() {
        return new WorkoutCreateDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View dialogView = inflater.inflate(R.layout.dialog_add_workout, container, false);

        ActivityComponent component = getActivityComponent();

        if (component != null) {
            component.inject(this);
            binder = ButterKnife.bind(this, dialogView);
        }

        return dialogView;
    }

    @OnClick(R.id.create_button)
    void onCreateButtonClick() {
        if (!workoutEditText.getText().toString().isEmpty()) {
            workoutEditText.setCursorVisible(false);
            dismiss();
            presenter.saveButtonClicked(workoutEditText.getText().toString());
        } else {
            errorHint.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        workoutEditText.setCursorVisible(false);
        binder.unbind();
    }
}
