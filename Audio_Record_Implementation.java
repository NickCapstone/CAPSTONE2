package ayy.capstone;

/**
 * Created by Nick on 2016-11-14.
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;



public class Audio_Record_Implementation {
    private static final int audio_sample_rate = 44100;
    private static final int audio_source = MediaRecorder.AudioSource.MIC;
    private static final int audio_channel = AudioFormat.CHANNEL_IN_MONO;
    private static final int audio_encoding = AudioFormat.ENCODING_PCM_FLOAT;




}
