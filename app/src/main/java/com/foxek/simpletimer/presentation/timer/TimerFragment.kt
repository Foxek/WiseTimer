package com.foxek.simpletimer.presentation.timer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.core.content.ContextCompat.startForegroundService
import androidx.core.view.isVisible
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.foxek.simpletimer.R
import com.foxek.simpletimer.presentation.base.BaseFragment
import com.foxek.simpletimer.presentation.timer.TimerService.LocalBinder
import com.foxek.simpletimer.common.utils.Constants.ACTION_PAUSE
import com.foxek.simpletimer.common.utils.Constants.ACTION_STOP
import com.foxek.simpletimer.common.utils.ServiceTools.isServiceRunning
import kotlinx.android.synthetic.main.fragment_timer.*

class TimerFragment : BaseFragment(), TimerContact.ServiceCallback {

    override val layoutId = R.layout.fragment_timer
    lateinit var serviceConnection: ServiceConnection

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragment_timer_pause_btn.setOnClickListener {
            startForegroundService(
                context!!,
                Intent(context, TimerService::class.java).setAction(ACTION_PAUSE)
            )
        }

        fragment_timer_reset_btn.setOnClickListener {
            startForegroundService(
                context!!,
                Intent(context, TimerService::class.java).setAction(ACTION_STOP)
            )
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
        startForegroundService(
            context!!,
            Intent(context, TimerService::class.java).setAction(ACTION_STOP)
        )
        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()

        if (isServiceRunning(context!!, TimerService::class.java)) {
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

        if (isServiceRunning(context!!, TimerService::class.java)) {
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

    override fun showCurrentIntervalTime(time: String) {
        fragment_timer_counter.text = time
    }

    override fun showRoundType(type: Int) {
        fragment_timer_counter_type.setText(type)
    }

    override fun showRoundInfo(currentName: String, nextName: String, number: String) {
        fragment_timer_counter_number.text = number
        
        fragment_timer_current_interval_name.isVisible = currentName.isNotBlank()
        fragment_timer_current_interval_name.text = currentName

        fragment_timer_next_interval_name.isVisible = nextName.isNotBlank()
        fragment_timer_next_interval_name.text = getString(R.string.timer_next_interval_hint, nextName)
    }
}