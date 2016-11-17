package ayy.capstone;

/*
*
*
*
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends AppCompatActivity {

    Audio_Record_Implementation recorder = new Audio_Record_Implementation();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recorder.startRecording(5);

    }


    /**
     * Called when the user clicks the Send button
     */
    public void alterMessage(View view) {
        recorder.stopRecording();
    }
}