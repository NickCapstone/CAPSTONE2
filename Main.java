package ayy.capstone;

/*
*
*
*
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static java.lang.Thread.sleep;

public class Main extends AppCompatActivity{

    Audio_Record_Implementation recorder = new Audio_Record_Implementation();
    private Draw_Graph graphingCanvas;
    private Handler updateGraphHandler;
    Signal_Processing signal_processor = new Signal_Processing();
    int sampleRate=0;
    int buffSize = 0;

    //TEST VALS
    double maxVal = 0;
    double largestFreq = 0;
    public long startTime = 0;
    public long endTime = 0;
    public int graphMode = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateGraphHandler = new Handler();
        graphingCanvas = (Draw_Graph) findViewById(R.id.graphCanvasView);
        recorder.startRecording(2*4096);
        sampleRate = recorder.getSampleRate();
        buffSize = recorder.getAudio_buffer_size();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        /* VERIFY IF FFT WORKS BY TESTING WITH IMPULSES - SHOULD ALL BE OF EQUAL MAGNITUDE - PASSED


        double[] testdata = new double[8];
        testdata[0] = 0;
        testdata[1] = 2;
        testdata[2] = -3;
        testdata[3] = 0;
        testdata[4] = 1;
        testdata[5] = 1;
        testdata[6] = -3;
        testdata[7] = 3;


        double[][]test = signal_processor.Periodogram(testdata,sampleRate);
        graphingCanvas.updateGraph(test[1],true,false);

*/
        System.out.println("starting processing...");
        runningLoop.run();


    }

    Runnable runningLoop = new Runnable(){
        @Override
        public void run(){
            int delay = 0;


            try {
                startTime = System.currentTimeMillis();
                double data[] = recorder.getAudioDataAsDouble();


                //data = signal_processor.firFilterLowPass(data);
                //data = signal_processor.firfilterDCBlocker(data);

                double[][]test = signal_processor.Periodogram(data,sampleRate,true);

                double[] cepstrum = new double[test[1].length];
                for(int i=0;i<test[1].length/2;i++){
                    cepstrum[i]=Math.log(test[1][i]);
                }

                //cepstrum = signal_processor.firfilterDCBlocker(cepstrum);


               double[]fftOfCepstrum = signal_processor.Periodogram(cepstrum,sampleRate,false)[1];


                for(int i=50;i<fftOfCepstrum.length;i++){
                    //fftOfCepstrum[i] = Math.abs(fftOfCepstrum[i])*Math.abs(fftOfCepstrum[i]);
                    if(fftOfCepstrum[i]>maxVal){
                        maxVal = fftOfCepstrum[i];
                        largestFreq = 22050/(i);
                    }
                }


                if(graphMode == 0) {
                    graphingCanvas.updateGraph(test[1], false, false, 0, test[1].length / 4);
                }else if(graphMode==1){
                    graphingCanvas.updateGraph(cepstrum,false,false,0,cepstrum.length / 4);
                }else if(graphMode==2){
                    graphingCanvas.updateGraph(fftOfCepstrum,false,false,50,fftOfCepstrum.length/2);
                }else{
                    graphingCanvas.updateGraph(data,true,true);
                }


                endTime = System.currentTimeMillis();
                System.out.println((endTime-startTime) + ", " + maxVal + ", " + largestFreq);


                maxVal = 0;

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
    public void button(View view) {
        switch (graphMode){
            case 0:
                graphMode = 1;
                break;
            case 1:
                graphMode = 2;
                break;
            case 2:
                graphMode = 3;
                break;
            case 3:
                graphMode = 0;
                break;
        }
    }




}