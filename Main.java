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
import java.lang.Math;

public class Main extends AppCompatActivity{

    Audio_Record_Implementation recorder = new Audio_Record_Implementation();
    private Draw_Graph graphingCanvas;
    private Handler updateGraphHandler;
    Signal_Processing signal_processor = new Signal_Processing();
    int sampleRate=0;


    //TESTING VARIABLES
    double maxVal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double[][] test = signal_processor.FFT(new double[]{1,2,3,1,2,0,1,1},2);

        updateGraphHandler = new Handler();
        graphingCanvas = (Draw_Graph) findViewById(R.id.graphCanvasView);
        recorder.startRecording(5);
        sampleRate = recorder.getSampleRate();
        //signal_processor.


    }

    Runnable runningLoop = new Runnable(){
        @Override
        public void run(){
            int delay = 50;
            try {
                double data[];
                Audio_Data_Manager audioData = recorder.getNewData();
                data = audioData.getMostRecentAsDouble();
                double[] dataToPowerOf2= new double[512];
                for(int i=0;i<512;i++){
                    dataToPowerOf2[i] = data[i];
                }
                double[][]test = signal_processor.FFT(dataToPowerOf2,sampleRate);
                for(int i=0;i<512/2;i++){
                    if(test[1][i]>maxVal){
                        maxVal = test[1][i];
                    }
                }
                System.out.println(maxVal);
                graphingCanvas.updateGraph(test[1],false,false);
                /*
                TEST OUTPUT VALUES
                for(int i=0;i<data.length;i++){
                System.out.println(data[i]);
                }
                */

                //graphingCanvas.updateGraph(data,true);

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