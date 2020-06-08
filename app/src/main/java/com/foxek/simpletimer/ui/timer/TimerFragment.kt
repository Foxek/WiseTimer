package com.foxek.simpletimer.ui.timer

import android.content.ComponentName
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
import com.foxek.simpletimer.utils.ServiceTools.isServiceRunning
import kotlinx.android.synthetic.main.activity_timer.*

class TimerFragment : BaseFragment(), TimerContact.ServiceCallback {

    override val layoutId = R.layout.activity_timer
    lateinit var serviceConnection: ServiceConnection

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragment_timer_pause_btn.setOnClickListener {
            startForegroundService(context!!, Intent(context, TimerService::class.java).setAction(ACTION_PAUSE))
        }

        fragment_timer_reset_btn.setOnClickListener {
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

        if (isServiceRunning(context!!,TimerService::class.java)) {
            context?.bindService(
                    Intent(context, TimerService::class.java),
                    serviceConnection,
                    0)
        } else {
            super.onBackPressed()
        }
    }

    override fun onStop() {
        super.onStop()

        if (isServiceRunning(context!!,TimerService::class.java)) {
            context?.unbindService(serviceConnection)
        }
    }

    override fun startWorkoutActivity() {
        super.onBackPressed()
    }

    override fun showPauseInterface() {
        fragment_timer_reset_btn.visibility = View.VISIBLE
        fragment_timer_pause_btn.setText(R.string.timer_continue_button)
    }

    override fun showPlayInterface() {
        fragment_timer_reset_btn.visibility = View.GONE
        fragment_timer_pause_btn.setText(R.string.timer_pause_button)
    }

    override fun showCurrentCounter(time: String) {
        fragment_timer_counter.text = time
    }

    override fun showCounterType(type: Int) {
        fragment_timer_counter_type.setText(type)
    }

    override fun showCounterNumber(number: String) {
        fragment_timer_counter_number.text = number
    }

    override fun showCounterName(name: String?) {
        if (name == Constants.EMPTY) {
            fragment_timer_interval_name.visibility = View.GONE
        } else {
            fragment_timer_interval_name.visibility = View.VISIBLE
            fragment_timer_interval_name.text = name
        }
    }
}