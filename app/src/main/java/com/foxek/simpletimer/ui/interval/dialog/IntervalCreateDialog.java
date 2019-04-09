package com.foxek.simpletimer.ui.interval.dialog;

public class IntervalCreateDialog {/*extends BaseDialog<IntervalContact.Presenter> implements IntervalContact.DialogView {

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

    @BindView(R.id.repeats_edit_text)
    EditText mRepeatText;

    @BindView(R.id.repeat_name)
    TextView mRepeatName;

    @BindView(R.id.repeat_checkBox)
    CheckBox mCheckBox;

    public static IntervalCreateDialog newInstance() {
        return new IntervalCreateDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View dialogView = inflater.inflate(R.layout.dialog_edit_interval, container, false);
        mBinder = ButterKnife.bind(this, dialogView);

        mDeleteButton.setVisibility(View.GONE);

        mDialogTitle.setText(R.string.dialog_interval_create_title);

        mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                mRepeatName.setVisibility(View.VISIBLE);
                mRepeatText.setVisibility(View.VISIBLE);
            }else{
                mRepeatName.setVisibility(View.GONE);
                mRepeatText.setVisibility(View.GONE);
            }
        });

        prepareEditText();
        getPresenter().attachDialog(this);
        getDialog().setCanceledOnTouchOutside(true);

        return dialogView;
    }

    private void prepareEditText() {
        mWorkMinuteText.setText(formatEditTextData(0));
        mWorkSecondText.setText(formatEditTextData(0));

        mRestMinuteText.setText(formatEditTextData(0));
        mRestSecondText.setText(formatEditTextData(0));

        mRepeatText.setText("1");
    }

    private void repairMemoryLeak() {
        mWorkMinuteText.setCursorVisible(false);
        mWorkSecondText.setCursorVisible(false);

        mRestMinuteText.setCursorVisible(false);
        mRestSecondText.setCursorVisible(false);

        mRepeatText.setCursorVisible(false);
    }

    @OnClick(R.id.save_button)
    public void onSaveButtonClick() {
        int work_time, rest_time, repeat;

        if (!mWorkMinuteText.getText().toString().equals("") && !mWorkSecondText.getText().toString().equals("")) {
            work_time = convertToSeconds(mWorkMinuteText.getText().toString(), mWorkSecondText.getText().toString());
            if (work_time == 0) work_time = 1;
        } else
            work_time = 1;


        if (!mRestMinuteText.getText().toString().equals("") && !mRestSecondText.getText().toString().equals("")) {
            rest_time = convertToSeconds(mRestMinuteText.getText().toString(), mRestSecondText.getText().toString());
            if (rest_time == 0) rest_time = 1;
        } else
            rest_time = 1;

        if (!mRepeatText.getText().toString().equals("")) {
            repeat = Integer.valueOf(mRepeatText.getText().toString());
            if (repeat == 0) repeat = 1;
        } else
            repeat = 1;

        for (int i=1; i<=repeat; i++)
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
    }*/
}