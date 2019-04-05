package com.foxek.simpletimer.ui.interval.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.ui.base.BaseDialog;
import com.foxek.simpletimer.ui.interval.IntervalContact;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WorkoutEditDialog extends BaseDialog<IntervalContact.Presenter> implements IntervalContact.DialogView{

    private Unbinder mBinder;

    @BindView(R.id.delete_button)
    TextView mDeleteButton;

    @BindView(R.id.save_button)
    TextView mSaveButton;

    @BindView(R.id.training_edit_text)
    EditText mWorkoutEditText;

    public static WorkoutEditDialog newInstance(String workoutName) {
        WorkoutEditDialog mWorkoutEditDialog = new WorkoutEditDialog();
        Bundle args = new Bundle();
        args.putString("workout_name", workoutName);
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

        View dialogView = inflater.inflate(R.layout.dialog_edit_training, container, false);
        mBinder = ButterKnife.bind(this, dialogView);

        mWorkoutEditText.setText(getArguments().getString("workout_name"));
        mWorkoutEditText.setSelection(mWorkoutEditText.getText().length());

        getPresenter().attachDialog(this);
        getDialog().setCanceledOnTouchOutside(true);

        mDeleteButton.setOnClickListener(v->{
            if (getShowsDialog()) {
                mWorkoutEditText.setCursorVisible(false);
                getDialog().cancel();
                getPresenter().deleteWorkout();
            }
            else
                dismiss();
        });

        mSaveButton.setOnClickListener(v->{
            if (!mWorkoutEditText.getText().toString().isEmpty()) {
                mWorkoutEditText.setCursorVisible(false);
                dismiss();
                getPresenter().editWorkout(mWorkoutEditText.getText().toString());
            }else{
                dismiss();
            }
        });

        return dialogView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWorkoutEditText.setCursorVisible(false);
        mBinder.unbind();
        getPresenter().detachDialog();
    }
}