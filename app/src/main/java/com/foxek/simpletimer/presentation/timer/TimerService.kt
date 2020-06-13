package com.foxek.simpletimer.presentation.timer

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.foxek.simpletimer.R
import com.foxek.simpletimer.common.extensions.observeOnMain
import com.foxek.simpletimer.data.model.Time
import com.foxek.simpletimer.data.timer.IntervalTimer
import com.foxek.simpletimer.presentation.MainActivity
import com.foxek.simpletimer.presentation.base.BaseService
import com.foxek.simpletimer.common.utils.Constants.ACTION_OPEN_TIMER
import com.foxek.simpletimer.common.utils.Constants.ACTION_PAUSE
import com.foxek.simpletimer.common.utils.Constants.ACTION_START
import com.foxek.simpletimer.common.utils.Constants.ACTION_STOP
import com.foxek.simpletimer.common.utils.Constants.EXTRA_WORKOUT_ID
import com.foxek.simpletimer.common.utils.Constants.POST_TIME_TYPE
import com.foxek.simpletimer.common.utils.Constants.REST_TIME_TYPE
import com.foxek.simpletimer.common.utils.Constants.WORK_TIME_TYPE
import com.foxek.simpletimer.common.utils.formatIntervalData
import com.foxek.simpletimer.common.utils.formatIntervalNumber
import com.foxek.simpletimer.common.utils.formatIntervalType
import com.foxek.simpletimer.domain.round.RoundInteractor
import com.foxek.simpletimer.domain.workout.WorkoutInteractor
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class TimerService : BaseService() {

    @Inject
    lateinit var workoutInteractor: WorkoutInteractor

    @Inject
    lateinit var roundInteractor: RoundInteractor

    @Inject
    lateinit var intervalTimer: IntervalTimer

    private var currentTime: Time? = null
    private var callback: TimerContact.ServiceCallback? = null

    override val notificationId = 1
    override val channelId = "ForegroundServiceChannel"

    override fun onCreate() {
        super.onCreate()

        component?.inject(this)

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            action = ACTION_OPEN_TIMER
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_UPDATE_CURRENT)

        val notification = createNotification {
            setContentIntent(pendingIntent)
            setSmallIcon(R.drawable.ic_stopwatch)
            setOnlyAlertOnce(true)
        }

        startForeground(notificationId, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return LocalBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        callback = null
        return true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        when (intent?.action) {
            ACTION_START -> handleStart(intent.getIntExtra(EXTRA_WORKOUT_ID, 0))
            ACTION_STOP -> handleStop()
            ACTION_PAUSE -> handlePause()
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        intervalTimer.stop()
    }

    fun registerServiceClient(timerCallback: TimerContact.ServiceCallback?) {
        callback = timerCallback
        callback?.run {
            currentTime?.let {
                showCurrentIntervalTime(formatIntervalData(it.value))
                showRoundType(formatIntervalType(it.type))
                showRoundInfo(it.name.orEmpty(), it.nextName.orEmpty(), formatIntervalNumber(it.position))

                if (intervalTimer.state == IntervalTimer.State.STARTED) {
                    showPlayInterface()
                } else {
                    showPauseInterface()
                }
            }
        }
    }

    private fun handlePause() {
        if (intervalTimer.state == IntervalTimer.State.STARTED) {
            intervalTimer.stop()
            callback?.showPauseInterface()
        } else {
            intervalTimer.restart()
            callback?.showPlayInterface()
        }
    }

    private fun handleStart(id: Int) {
        prepareIntervals(id)
        registerTimerCallback()
        registerTickCallback()
    }

    private fun handleStop() {
        stopSelf()
        callback?.startWorkoutActivity()
    }

    private fun handleFinish(time: Time) {
        currentTime = time
        when (time.type) {
            REST_TIME_TYPE -> callback?.showRoundType(R.string.timer_rest_time)
            WORK_TIME_TYPE -> {
                updateNotification { setContentTitle(formatIntervalNumber(time.position)) }
                callback?.let {
                    it.showRoundType(R.string.timer_work_time)
                    it.showRoundInfo(time.name.orEmpty(), time.nextName.orEmpty(), formatIntervalNumber(time.position))
                }
            }
            POST_TIME_TYPE -> handleStop()
        }
    }

    private fun handleTick(time: Int) {
        currentTime?.value = time
        callback?.showCurrentIntervalTime(formatIntervalData(time))
        updateNotification {
            setContentText("${getString(formatIntervalType(currentTime?.type))}: ${formatIntervalData(time)}")
        }
    }

    private fun prepareIntervals(workoutId: Int) {
        callback?.showRoundType(R.string.timer_prepare)

        workoutInteractor.getWorkoutVolumeState(workoutId)
            .observeOnMain()
            .subscribeBy(onSuccess = intervalTimer::enableSound)
            .disposeOnDestroy()

        roundInteractor.getRounds(workoutId)
            .observeOnMain()
            .subscribeBy(onSuccess = intervalTimer::prepare)
            .disposeOnDestroy()
    }

    private fun registerTimerCallback() {
        intervalTimer.onIntervalFinished
            .observeOnMain()
            .subscribeBy(onNext = ::handleFinish)
            .disposeOnDestroy()
    }

    private fun registerTickCallback() {
        intervalTimer.onTimerTickHappened
            .observeOnMain()
            .subscribeBy(onNext = ::handleTick)
            .disposeOnDestroy()
    }

    inner class LocalBinder : Binder() {
        val instance: TimerService
            get() = this@TimerService
    }
}
