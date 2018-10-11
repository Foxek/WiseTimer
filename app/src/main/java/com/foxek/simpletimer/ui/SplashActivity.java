package com.foxek.simpletimer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.foxek.simpletimer.ui.workout.WorkoutActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
        finish();
    }

}
