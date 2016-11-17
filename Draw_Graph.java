package ayy.capstone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.Process;
import android.support.annotation.Dimension;
import android.support.annotation.RequiresApi;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Nick on 2016-11-16.
 *
 * Class responsible for drawing and updating graph continually.
 *
 */

public class Draw_Graph extends SurfaceView implements SurfaceHolder.Callback {

    private Paint painter = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    private Thread graphThread = null;

    public Draw_Graph(Context context){
        super(context);


        painter.setStyle(Paint.Style.STROKE);
        painter.setStrokeWidth(3);
        painter.setColor(Color.GREEN);


        path.moveTo(0,150);
        path.lineTo(200,200);

        getHolder().addCallback(this);

    }

    private void callGraphingThread(final SurfaceHolder localHolder){
        graphThread = new Thread(new Runnable(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void run(){
                android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_DISPLAY);
                Canvas canvas = null;

                try {

                    canvas = localHolder.lockCanvas(null);


                    synchronized(localHolder) {
                        System.out.println(canvas.getHeight());
                        onDraw(canvas);
                    }

                } finally {

                    if(canvas != null) {

                        localHolder.unlockCanvasAndPost(canvas);
                    }

                }
            }

        }, "Thread to plot data"
        );
        graphThread.start();
    }



    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect((float) 9,(float) 0, (float) 50, (float) 50,painter);
        canvas.drawPath(path,painter);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        //nothing
    }

    public void surfaceCreated(SurfaceHolder holder) {

        callGraphingThread(getHolder());

    }


    public void surfaceDestroyed(SurfaceHolder holder) {
        graphThread.interrupt();
        graphThread = null;
    }


}
