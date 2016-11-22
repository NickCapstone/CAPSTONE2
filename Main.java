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
import ayy.capstone.Complex_Number;

public class Main extends AppCompatActivity{

    Audio_Record_Implementation recorder = new Audio_Record_Implementation();
    private Draw_Graph graphingCanvas;
    private Handler updateGraphHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Complex_Number a = new Complex_Number(2,2);
        Complex_Number b = new Complex_Number(6,9);
        Complex_Number c = a.mult(b);
        System.out.println(c.re());
        System.out.println(c.im());

        updateGraphHandler = new Handler();
        graphingCanvas = (Draw_Graph) findViewById(R.id.graphCanvasView);
        recorder.startRecording(5);


    }

    Runnable runningLoop = new Runnable(){
        @Override
        public void run(){
            int delay = 50;
            try {
                float data[];
                Audio_Data_Manager audioData = recorder.getNewData();
                data = audioData.getMostRecentAsFloat();

                /*
                TEST OUTPUT VALUES
                for(int i=0;i<data.length;i++){
                System.out.println(data[i]);
                }
                */

                graphingCanvas.updateGraph(data,true);

            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                updateGraphHandler.postDelayed(runningLoop, delay);
            }
        }



    };


    /**
     * Called when the user clicks the Send button
     */
    public void clearGraph(View view) {
        runningLoop.run();
    }




}