package ayy.capstone;

/**
 * Created by Nick on 2016-11-14.
 *
 * Retrieves data from microphone and sends to AudioDataManager for handling
 *
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.media.AudioManager;
import android.media.MediaFormat;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import ayy.capstone.Audio_Data_Manager;

public class Audio_Record_Implementation {
    private static int number_of_intervals = 0;
    private static final int audio_sample_rate = 44100;
    private static final int audio_source = MediaRecorder.AudioSource.MIC;
    private static final int audio_channel = AudioFormat.CHANNEL_IN_MONO;
    private static final int audio_encoding = AudioFormat.ENCODING_PCM_16BIT;
    private static int audio_buffer_size = AudioRecord.getMinBufferSize(audio_sample_rate,audio_channel,audio_encoding);
    private static boolean shouldRecord = false;

    /*
        Using 16-bit encoding, so   audio_buffer_size/2 = #samples/Buffer

                                    time per sample = #samples/buffer / (audio_sample_rate)
     */

    private AudioRecord audio_record;
    private Audio_Data_Manager audio_data;

    private Thread saveAudioDataThread = null;

    public void startRecording(int numberOfIntervalsToSave){
        if(numberOfIntervalsToSave<1){
            number_of_intervals = 1;
        }else{
            number_of_intervals = numberOfIntervalsToSave;
        }

        shouldRecord = true;
        record();
    }

    public Audio_Data_Manager getNewData(){
        return audio_data;
    }



    public void stopRecording(){
        shouldRecord = false;
    }

    private void record(){
        //Start a seperate thread to save audio data
        saveAudioDataThread = new Thread(new Runnable(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void run(){
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);
                int count = 0;
                short copiedData[];
                int sizeOfShortArray;

                if (audio_buffer_size == AudioRecord.ERROR || audio_buffer_size == AudioRecord.ERROR_BAD_VALUE){
                    audio_buffer_size = 2* audio_sample_rate;
                }

                sizeOfShortArray = audio_buffer_size/2;
                copiedData =new short[sizeOfShortArray];
                audio_data = new Audio_Data_Manager(number_of_intervals,sizeOfShortArray);

                audio_record = new AudioRecord(
                        audio_source,
                        audio_sample_rate,
                        audio_channel,
                        audio_encoding,
                        audio_buffer_size
                );

                audio_record.startRecording();

                while(shouldRecord){
                    audio_record.read(copiedData,0,copiedData.length);
                    audio_data.add(copiedData);
                }
                audio_record.stop();
                audio_record.release();
            }

        }, "Thread to save audio data"
        );

        saveAudioDataThread.start();
    }






}
