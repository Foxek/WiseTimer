package com.foxek.simpletimer.ui

import android.content.Intent
import android.os.Bundle

import com.foxek.simpletimer.ui.workout.WorkoutActivity

import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, WorkoutActivity::class.java)
        startActivity(intent)
        finish()
    }

}
