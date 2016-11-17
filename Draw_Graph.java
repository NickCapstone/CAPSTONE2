package ayy.capstone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Process;
import android.support.annotation.Dimension;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import java.lang.Math;


/**
 * Created by Nick on 2016-11-16.
 *
 * Class responsible for drawing and updating graph continually.
 *
 */

public class Draw_Graph extends View {

    private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Path path = new Path();
    private Thread graphThread = null;

    private int wGraph; //Width of graph area
    private int hGraph; //Height of graph area

    private int ticks;//number of times user pressed button.

    public Draw_Graph(Context context, AttributeSet attributes) {
        super(context, attributes);


        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3);
        linePaint.setColor(Color.GREEN);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(30);
        borderPaint.setColor(Color.BLACK);

        //set start, end

        callGraphingThread();
    }

    //create a thread that will run continually on the side
    private void callGraphingThread() {
        graphThread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void run() {
                android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_DISPLAY);
            }

        }, "Thread to plot data"
        );
        graphThread.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,wGraph,hGraph,borderPaint);
        canvas.drawPath(path, linePaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.wGraph = w;
        this.hGraph = h;
        path.moveTo(0,hGraph/2);
        path.lineTo(wGraph,hGraph/2);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void changePath(float[] points){
        float maxY = maxYInDataset(points);
        boolean firstPoint = true;
        path.reset();
        path.moveTo(0,0);
        int numPoints = points.length;
        int xPointsArray[] = new int[numPoints];
        int yPointsArray[] = new int[numPoints];


        for(int i=0;i<numPoints;i++){
            if(firstPoint){
                path.moveTo(normalizeX(i,numPoints),normalizeY(points[i],2000));
                firstPoint = false;
            }else {
                path.lineTo(normalizeX(i,numPoints),normalizeY(points[i],2000));
                System.out.println(normalizeY(points[i],2000));
            }
        }

        invalidate();

    }

    //normalize the X values, given the number of points and knowing the total width.
    public float normalizeX(int x,int numPoints){
        float portionOfTotal = 0;
        portionOfTotal = (float) x / (float) numPoints;
        return portionOfTotal * this.wGraph;
    }



    //calculates the value of Y based on its value, the range of Y values, and the total height.
    public float normalizeY(float y, float maxY){
        float returnVal;
        int midPoint = this.hGraph/2;
        int availableAmplitudeSpace = this.hGraph/2;
        float portionOfMaxY = 0;
        portionOfMaxY = Math.abs((float) y)/maxY;

        if(y>0){
            //y>=, we want to go "up" on the page, i.e subtract portion of "amplitude space(h/2) from the midpoint.
            returnVal = (midPoint)-(portionOfMaxY * (availableAmplitudeSpace));
        }else if(y<0){
            returnVal = (midPoint)+(portionOfMaxY * (availableAmplitudeSpace));
        }else {
            //else y = 0
            returnVal = midPoint;
        }

        return returnVal;

    }

    //find the maximum value of y in the given dataset
    public float maxYInDataset(float[] points){
        float maxY = 0;
        for(int i=0;i<points.length;i++){
            if( Math.abs(points[i])>maxY){
                maxY = Math.abs(points[i]);
            }
        }
        return maxY;
    }
}
