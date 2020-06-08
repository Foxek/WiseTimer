package com.foxek.simpletimer.ui.timer

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.foxek.simpletimer.R
import com.foxek.simpletimer.data.model.Time
import com.foxek.simpletimer.data.timer.IntervalTimer
import com.foxek.simpletimer.ui.MainActivity
import com.foxek.simpletimer.ui.base.BaseService
import com.foxek.simpletimer.utils.Constants.ACTION_OPEN_TIMER
import com.foxek.simpletimer.utils.Constants.ACTION_PAUSE
import com.foxek.simpletimer.utils.Constants.ACTION_START
import com.foxek.simpletimer.utils.Constants.ACTION_STOP
import com.foxek.simpletimer.utils.Constants.EXTRA_WORKOUT_ID
import com.foxek.simpletimer.utils.Constants.POST_TIME_TYPE
import com.foxek.simpletimer.utils.Constants.REST_TIME_TYPE
import com.foxek.simpletimer.utils.Constants.WORK_TIME_TYPE
import com.foxek.simpletimer.utils.formatIntervalData
import com.foxek.simpletimer.utils.formatIntervalNumber
import com.foxek.simpletimer.utils.formatIntervalType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TimerService : BaseService() {

    @Inject
    lateinit var interactor: TimerContact.Interactor

    private var currentTime: Time? = null
    private var callback: TimerContact.ServiceCallback? = null

    override val notificationId = 1
    override val channelId = "ForegroundServiceChannel"

    override fun onCreate() {
        super.onCreate()

        component?.inject(this)

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.action = ACTION_OPEN_TIMER

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
        interactor.deleteDependencies()
    }

    private fun handlePause() {
        if (interactor.getTimerState() == IntervalTimer.State.STARTED) {
            interactor.stopTimer()
            callback?.showPauseInterface()
        } else {
            interactor.continueTimer()
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
            REST_TIME_TYPE -> callback?.showCounterType(R.string.timer_rest_time)
            WORK_TIME_TYPE -> {
                updateNotification { setContentTitle(formatIntervalNumber(time.position)) }
                callback?.let {
                    it.showCounterType(R.string.timer_work_time)
                    it.showCounterName(time.name)
                    it.showCounterNumber(formatIntervalNumber(time.position))
                }
            }
            POST_TIME_TYPE -> handleStop()
        }
    }

    private fun handleTick(time: Int) {
        currentTime?.value = time
        callback?.showCurrentCounter(formatIntervalData(time))
        updateNotification {
            setContentText("${getString(formatIntervalType(currentTime?.type))}: ${formatIntervalData(time)}")
        }
    }

    private fun prepareIntervals(workoutId: Int) {
        callback?.showCounterType(R.string.timer_prepare)
        disposable.add(interactor.getVolume(workoutId))
        disposable.add(interactor.fetchIntervalList(workoutId))
    }

    private fun registerTimerCallback() {
        disposable.add(interactor.intervalFinishedCallback()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ handleFinish(it) }, { })
        )
    }

    private fun registerTickCallback() {
        disposable.add(interactor.tickCallback()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ handleTick(it) }, { })
        )
    }

    inner class LocalBinder : Binder() {
        val instance: TimerService
            get() = this@TimerService
    }

    fun registerServiceClient(timerCallback: TimerContact.ServiceCallback?) {
        callback = timerCallback
        if ((this.callback != null) and (currentTime != null)) {

            callback?.showCurrentCounter(formatIntervalData(currentTime?.value!!))
            callback?.showCounterType(formatIntervalType(currentTime?.type))
            callback?.showCounterName(currentTime?.name)
            callback?.showCounterNumber(formatIntervalNumber(currentTime?.position!!))

            if (interactor.getTimerState() == IntervalTimer.State.STARTED) {
                callback?.showPlayInterface()
            } else {
                callback?.showPauseInterface()
            }
        }
    }
}
