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
import ayy.capstone.Draw_Graph;
import ayy.capstone.Audio_Data_Manager;

public class Main extends AppCompatActivity{

    Audio_Record_Implementation recorder = new Audio_Record_Implementation();
    private Draw_Graph graphingCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graphingCanvas = (Draw_Graph) findViewById(R.id.graphCanvasView);

        recorder.startRecording(5);


    }

    private void runningLoop(){


            float data[];
            Audio_Data_Manager audioData = recorder.getNewData();
            data = audioData.getMostRecentAsFloat();
        /*
        for(int i=0;i<data.length;i++){
            System.out.println(data[i]);
        }
        */
            graphingCanvas.changePath(data);

    }


    /**
     * Called when the user clicks the Send button
     */
    public void clearGraph(View view) {
        runningLoop();
    }




}