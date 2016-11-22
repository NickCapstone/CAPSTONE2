package ayy.capstone;

/**
 * Created by Nick on 2016-11-14.
 */

import ayy.capstone.Complex_Number;

public class Signal_Processing {

    // NUMBER OF POINTS MUST BE A POWER OF 2!
    public Complex_Number[] fft_recursive(Complex_Number vals[]){
        //Recursive Cooley-Tukey FFT
        int n = vals.length;

        if (n==1){
            return vals;
        }

        if (n % 2 != 0) { throw new RuntimeException("invalid n");
        }

        Complex_Number[] evens = new Complex_Number[n/2];
        for(int i=0;i<n/2;i=i+2){
            evens[i] = vals[i];
        }

        Complex_Number[] odds = new Complex_Number[n/2];
        for(int i=1;i<n/2;i=i+2){
            odds[i]=vals[i];
        }

        for(int k = 0;k<(n/2);k++){

        }
        return null;
    }

}
