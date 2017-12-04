package com.example.jin20.audioplayersd;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
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
        FilePickerBuilder.getInstance().setMaxCount(1)
                .addFileSupport("test", new String[]{".mp3"})
                .setActivityTheme(R.style.AppTheme)
                .pickFile(this);
    }

    public void stop(View view) {
        releaseMP();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE_DOC:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    List<String>  docPaths = new ArrayList<>();
                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                    Log.d("test doc", "" + docPaths.get(0));
                    play(docPaths.get(0));
                }
                break;
        }
    }

    private void play(String path) {
        releaseMP();

        Log.d(LOG_TAG, "start SD");
        mediaPlayer = HybridMediaPlayer.getInstance(this);
        mediaPlayer.setDataSource(path);
        //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.prepare();
        mediaPlayer.play();
    }

}
