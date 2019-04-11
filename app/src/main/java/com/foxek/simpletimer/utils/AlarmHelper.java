package com.foxek.simpletimer.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.foxek.simpletimer.di.ApplicationContext;

import java.io.IOException;

import javax.inject.Inject;

import static android.content.Context.VIBRATOR_SERVICE;

public class AlarmHelper {

    private Context     context;
    private Vibrator    vibrator;
    private SoundPool   soundPool;
    private int         beepSound, longBeepSound, volume;

    @Inject
    AlarmHelper(@ApplicationContext Context context){
        this.context = context;
        vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        createNewSoundPool();
    }

    public void oneShotVibrate(){
        if (Build.VERSION.SDK_INT >= 26)
            vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        else
            vibrator.vibrate(500);
    }

    public void patternVibrate(){
        long[] pattern = {0, 800, 200, 800, 200, 800};
        if (Build.VERSION.SDK_INT >= 26)
            vibrator.vibrate(VibrationEffect.createWaveform(pattern,-1));
        else
            vibrator.vibrate(pattern,-1);
    }

    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

        beepSound = loadSound("beep.mp3");
        longBeepSound = loadSound("long_beep.mp3");
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd = null;
        try {
            afd = context.getAssets().openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return soundPool.load(afd, 1);
    }

    public void playLongSound(){
        soundPool.play(longBeepSound, volume, volume, 1, 0, 1);
    }

    public void playSound(){
        soundPool.play(beepSound, volume, volume, 1, 0, 1);
    }

    public  void playFinalSound(){
        soundPool.play(longBeepSound, volume, volume, 1, 2, 1);
    }

    public void setVolume(Boolean state){
        volume = (state) ? 1 : 0;
    }
}
