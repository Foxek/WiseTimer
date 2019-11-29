package com.foxek.simpletimer.ui.timer

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.core.content.ContextCompat.startForegroundService
import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.base.BaseFragment
import com.foxek.simpletimer.ui.timer.TimerService.LocalBinder
import com.foxek.simpletimer.utils.Constants
import com.foxek.simpletimer.utils.Constants.ACTION_PAUSE
import com.foxek.simpletimer.utils.Constants.ACTION_STOP
import kotlinx.android.synthetic.main.activity_timer.*

class TimerFragment : BaseFragment(), TimerContact.ServiceCallback {

    override val layoutId = R.layout.activity_timer
    lateinit var serviceConnection: ServiceConnection

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        pauseButton.setOnClickListener {
            startForegroundService(context!!, Intent(context, TimerService::class.java).setAction(ACTION_PAUSE))
        }

        resetButton.setOnClickListener {
            startForegroundService(context!!, Intent(context, TimerService::class.java).setAction(ACTION_STOP))
        }

        serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {}

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val timerService = (service as LocalBinder).instance
                timerService.registerServiceClient(this@TimerFragment)
            }

        }
    }

    override fun onBackPressed() {
        startForegroundService(context!!, Intent(context, TimerService::class.java).setAction(ACTION_STOP))
        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        context?.bindService(
                Intent(context, TimerService::class.java),
                serviceConnection,
                BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        context?.unbindService(serviceConnection)
    }

    override fun startWorkoutActivity() {
        onBackPressed()
    }

    override fun showPauseInterface() {
        resetButton.visibility = View.VISIBLE
        pauseButton.setText(R.string.timer_continue_button)
    }

    override fun showPlayInterface() {
        resetButton.visibility = View.GONE
        pauseButton.setText(R.string.timer_pause_button)
    }

    override fun showCurrentCounter(time: String) {
        counterView.text = time
    }

    override fun showCounterType(type: Int) {
        counterType.setText(type)
    }

    override fun showCounterNumber(number: String) {
        counterNumber.text = number
    }

    override fun showCounterName(name: String?) {
        if (name == Constants.EMPTY) {
            intervalName.visibility = View.GONE
        } else {
            intervalName.visibility = View.VISIBLE
            intervalName.text = name
        }
    }
}