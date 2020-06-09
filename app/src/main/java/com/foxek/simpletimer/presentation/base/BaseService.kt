package com.foxek.simpletimer.presentation.base

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.foxek.simpletimer.AndroidApplication
import com.foxek.simpletimer.di.component.DaggerServiceComponent
import com.foxek.simpletimer.di.component.ServiceComponent
import com.foxek.simpletimer.di.module.ServiceModule
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseService : Service() {

    abstract val notificationId: Int
    abstract val channelId: String

    lateinit var notificationManager: NotificationManager
    lateinit var notificationBuilder: NotificationCompat.Builder

    var component: ServiceComponent? = null
        private set

    var disposables = CompositeDisposable()

    override fun onCreate() {
        super.onCreate()

        component = DaggerServiceComponent.builder()
            .serviceModule(ServiceModule())
            .applicationComponent(AndroidApplication.component)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager = getSystemService(NotificationManager::class.java)
            notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
        } else {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationBuilder = NotificationCompat.Builder(applicationContext)
        }
    }

    inline fun updateNotification(body: NotificationCompat.Builder.() -> Unit) {
        notificationBuilder.body()
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    inline fun createNotification(body: NotificationCompat.Builder.() -> Unit): Notification {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(serviceChannel)
        }

        notificationBuilder.body()
        return notificationBuilder.build()
    }

    protected fun Disposable.disposeOnDestroy() {
        disposables.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}