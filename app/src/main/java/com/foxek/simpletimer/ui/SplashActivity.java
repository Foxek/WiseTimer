package com.foxek.simpletimer.ui;

import android.content.Intent;
import android.os.Bundle;

import com.foxek.simpletimer.ui.workout.WorkoutActivity;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
        finish();
    }

}
