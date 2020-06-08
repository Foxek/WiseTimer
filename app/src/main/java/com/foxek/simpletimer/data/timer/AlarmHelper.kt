package com.foxek.simpletimer.data.timer

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

import java.io.IOException

import javax.inject.Inject

import android.content.Context.VIBRATOR_SERVICE

class AlarmHelper @Inject constructor(private val context: Context) {
    private val vibrator: Vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    private var soundPool: SoundPool? = null
    private var beepSound: Int = 0
    private var longBeepSound: Int = 0
    private var volume: Int = 0

    init {
        createNewSoundPool()
    }

    fun oneShotVibrate() {
        if (Build.VERSION.SDK_INT >= 26)
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        else
            vibrator.vibrate(500)
    }

    fun patternVibrate() {
        val pattern = longArrayOf(0, 800, 200, 800, 200, 800)
        if (Build.VERSION.SDK_INT >= 26)
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
        else
            vibrator.vibrate(pattern, -1)
    }

    private fun createNewSoundPool() {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()

        beepSound = loadSound("beep.mp3")
        longBeepSound = loadSound("long_beep.mp3")
    }

    private fun loadSound(fileName: String): Int {
        var afd: AssetFileDescriptor? = null
        try {
            afd = context.assets.openFd(fileName)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return soundPool!!.load(afd, 1)
    }

    fun playLongSound() {
        soundPool!!.play(longBeepSound, volume.toFloat(), volume.toFloat(), 1, 0, 1f)
    }

    fun playSound() {
        soundPool!!.play(beepSound, volume.toFloat(), volume.toFloat(), 1, 0, 1f)
    }

    fun playFinalSound() {
        soundPool!!.play(longBeepSound, volume.toFloat(), volume.toFloat(), 1, 2, 1f)
    }

    fun setVolume(state: Boolean) {
        volume = if (state) 1 else 0
    }
}
