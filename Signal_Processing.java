package ayy.capstone;

/**
 * Created by Nick on 2016-11-14.
 */

import ayy.capstone.Complex_Number;

public class Signal_Processing {

    public double[][] FFT(double dataset[],int sampleRate){
        int k = dataset.length;
        double delta_f = sampleRate/k;

        Complex_Number[] complex_dataset = new Complex_Number[k];
        for(int i=0;i<k;i++){
            complex_dataset[i] = new Complex_Number(dataset[i],0);
        }
        Complex_Number[] FFTValues = fft_recursive(complex_dataset);

        for(int i=0;i<k;i++){
            //need to scale by dividing values by the number of points
            FFTValues[i] = FFTValues[i].scale(k);
        }

        //list of points, in [f,value]
        double[][] valuesFreqPoints = new double[2][k/2];
        for (int i = 0; i < k/2; i++) {
            valuesFreqPoints[0][i] =i*delta_f;
            valuesFreqPoints[1][i] = FFTValues[i].magnitude();
        }
        return valuesFreqPoints;
    }

    // NUMBER OF POINTS MUST BE A POWER OF 2!
    // see https://en.wikipedia.org/wiki/Cooley%E2%80%93Tukey_FFT_algorithm
    private Complex_Number[] fft_recursive(Complex_Number x[]){
        //Recursive Cooley-Tukey FFT
        int n = x.length;
        if(n==1){
            return new Complex_Number[]{x[0]};
        }

        if (n % 2 != 0) { throw new RuntimeException("n must be a power of 2");}

        Complex_Number[] evens = new Complex_Number[n/2];
        for(int i = 0; i<n/2;i++){
            evens[i] = x[2*i];
        }

        Complex_Number[] odds = new Complex_Number[n/2];
        for(int i = 0; i<n/2;i++){
            odds[i] = x[2*i+1];
        }

        Complex_Number[] E = fft_recursive(evens);
        Complex_Number[] O = fft_recursive(odds);

        Complex_Number[]y = new Complex_Number[n];
        for(int k=0;k<n/2;k++){
            double kthTerm = -2*Math.PI*k/n;
            Complex_Number expTerm = new Complex_Number(Math.cos(kthTerm),Math.sin(kthTerm));
            y[k] = E[k].add(expTerm.mult(O[k]));
            y[k+n/2] = E[k].sub(expTerm.mult(O[k]));
        }

        return y;
    }

}
