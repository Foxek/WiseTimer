package com.foxek.simpletimer.ui.workout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.ui.base.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WorkoutDialog extends BaseDialog<WorkoutContact.Presenter> implements WorkoutContact.DialogView{

    private Unbinder mBinder;

    @BindView(R.id.create_button)
    TextView mPositiveButton;

    @BindView(R.id.error_hint)
    TextView mErrorHint;

    @BindView(R.id.training_edit_text)
    EditText mTrainingEditText;

    public static WorkoutDialog newInstance() {
        return new WorkoutDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View dialogView = inflater.inflate(R.layout.dialog_add_training, container, false);
        mBinder = ButterKnife.bind(this, dialogView);

        getPresenter().attachDialog(this);

        mPositiveButton.setOnClickListener(v->{
            if (!mTrainingEditText.getText().toString().isEmpty()) {
                mTrainingEditText.setCursorVisible(false);
                dismiss();
                getPresenter().createNewWorkout(mTrainingEditText.getText().toString());
            }else{
                mErrorHint.setVisibility(View.VISIBLE);
            }
        });

        return dialogView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTrainingEditText.setCursorVisible(false);
        mBinder.unbind();
        getPresenter().detachDialog();
    }
}
