package ayy.capstone;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class Main extends AppCompatActivity {

    TextView modifiedMessage;
    EditText typedMessage;
    GraphView graph;
    static int x = 0;
    static int numPoints = 10;
    Handler updateGraph = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modifiedMessage = (TextView) findViewById(R.id.textView);
        typedMessage = (EditText) findViewById(R.id.edit_message);
        graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

    }

    public void test() {
        Double newVal= 1.0;

        // use to set manually numPoints=90;

        newVal = Double.valueOf(typedMessage.getText().toString());
        modifiedMessage.setText("There are currently ".concat(newVal.toString()).concat("points"));
        numPoints = newVal.intValue()-1;

        double[] vals = new double[numPoints];
        DataPoint[] pts = new DataPoint[numPoints];

        //SystemClock.sleep(1);
        for (int i = x; i < x + numPoints; i++) {
            vals[i - x] = Math.sin(i * Math.PI / (numPoints));
            pts[i-x] = new DataPoint((i-x),vals[i-x]);
        }

       // modifiedMessage.append(typedMessage.getText().toString());

        x = x+1;

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(pts);





        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(numPoints-1);
        graph.getViewport().setMinY(-1);
        graph.getViewport().setMaxY(1);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.removeAllSeries();
        graph.addSeries(series);
    }


    /** Called when the user clicks the Send button */
    public void alterMessage(View view) {
        updateGraph.post(runnableCode);
    }
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            // Do something here on the main thread
            test();
            Log.d("Handlers", "Called on main thread");
            // Repeat this the same runnable code block again another 1 seconds
            updateGraph.postDelayed(runnableCode, 300);
        }
    };

}






