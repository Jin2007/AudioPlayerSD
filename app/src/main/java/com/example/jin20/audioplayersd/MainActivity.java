package com.example.jin20.audioplayersd;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import hybridmediaplayer.HybridMediaPlayer;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    final String LOG_TAG = "myLogs";
    final String DATA_SD = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
            + "/storage/emulated/0/Ringtones/Adept - Carry the Weight.mp3";

    HybridMediaPlayer mediaPlayer;
    AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        am = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickStart(View view) {
        releaseMP();

        switch (view.getId()) {
            case R.id.btnStartSD:
                Log.d(LOG_TAG, "start SD");
                mediaPlayer = HybridMediaPlayer.getInstance(this);
                mediaPlayer.setDataSource("/storage/emulated/0/Ringtones/Adept - Carry the Weight.mp3");
                //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
                mediaPlayer.play();
                break;
        }

        if (mediaPlayer == null)
            return;

        //mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(LOG_TAG, "onCompletion");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }
}
