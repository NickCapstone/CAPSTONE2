package ayy.capstone;

/**
 * Created by Nick on 2016-11-04.
 * Testing audio capture and trying
 * to figure it out.
 *
 * NEEDS TO RUN ON PHONE****
 *
 */

import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

public class AudioCapture {
    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    public AudioCapture() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "recordedAudio";
    }

    private void onPlay(boolean start){
        if(start){
            startPlaying();
        }else{
            stopPlaying();
        }
    }

    public void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    public void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }



}
