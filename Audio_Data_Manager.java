package ayy.capstone;

/**
 * Created by Nick on 2016-11-14.
 *
 * Stores p_number_of_intervals intervals and once it fills up p_all_data, it signals the DSP to update its data.
 *
 * Perhaps it would be better to signal back to the audio implementation which could then signal the DSP.
 *
 * This will happen periodically at a fixed frequency - dependant upon how quickly data is saved here. This depends
 * on the sampling frequency and buffer size.
 */


public class Audio_Data_Manager {
    private short[][] p_all_data = null;
    private int p_number_of_intervals = 0;
    private int p_current_interval = 0;
    private int p_points_per_interval = 0;

    Audio_Data_Manager(int numIntervals, int pointsPerInterval){
        p_number_of_intervals = numIntervals;
        p_points_per_interval = pointsPerInterval;
        p_all_data = new short[p_number_of_intervals][p_points_per_interval];
    }

    public void add(short[] dataIn){
        p_all_data[p_current_interval] = dataIn;
        p_current_interval ++;
        //once we reach p_number_of_intervals-1, we have stored p_number_of_intervals. Signal update.
        if(p_current_interval>=(p_number_of_intervals-1)){
            p_current_interval= 0;
            //Dont do anything to the contents of p_all_data. It will all be overwritten before the next group is sent.
        }else{
        }


    }


    public double[] getMostRecentAsDouble(){
        return makeDouble(p_all_data[p_current_interval]);

    }

    private double[] makeDouble(short[] data) {
        double[] returnedArray = new double[data.length];
        for (int i = 0; i < returnedArray.length; i++) {
            returnedArray[i] = data[i];
        }
        return returnedArray;
    }


}
