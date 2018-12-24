package com.foxek.simpletimer.data;

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

    private Context     mContext;
    private Vibrator    mVibrator;
    private SoundPool   mSoundPool;
    private int         mBeepSound,mLongBeepSound;

    @Inject
    public AlarmHelper(@ApplicationContext Context context){
        mContext = context;
        mVibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        createNewSoundPool();
    }

    public void oneShotVibrate(){
        if (Build.VERSION.SDK_INT >= 26)
            mVibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        else
            mVibrator.vibrate(500);
    }

    public void patternVibrate(){
        long[] pattern = {0, 800, 200, 800, 200, 800};
        if (Build.VERSION.SDK_INT >= 26)
            mVibrator.vibrate(VibrationEffect.createWaveform(pattern,-1));
        else
            mVibrator.vibrate(pattern,-1);
    }

    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

        mBeepSound = loadSound("beep.mp3");
        mLongBeepSound = loadSound("long_beep.mp3");
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd = null;
        try {
            afd = mContext.getAssets().openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mSoundPool.load(afd, 1);
    }

    public void playLongSound(){
        mSoundPool.play(mLongBeepSound, 1, 1, 1, 0, 1);
    }

    public void playSound(){
        mSoundPool.play(mBeepSound, 1, 1, 1, 0, 1);
    }

    public  void playFinalSound(){
        mSoundPool.play(mLongBeepSound, 1, 1, 1, 2, 1);
    }
}
