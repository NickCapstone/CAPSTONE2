package ayy.capstone;

/**
 * Created by Nick on 2016-11-21.
 */

import java.lang.Math;

public class Complex_Number {
    private double pRe;
    private double pIm;

    public Complex_Number(double re, double im){
        pRe = re;
        pIm = im;
    }

    public double re(){
        return pRe;
    }

    public double im(){
        return pIm;
    }

    public double magnitude(){
        return Math.sqrt(pRe*pRe + pIm*pIm);
    }

    public double phase() {
        return Math.atan2(pIm, pRe);
    }

    public Complex_Number add(Complex_Number input){
        //Simply (a+bi)+(c+di) = (a+c)+i(b+d)
        return new Complex_Number((this.re()+input.re()),(this.im()+input.im()));
    }

    public Complex_Number sub(Complex_Number input){
        //same as add but subtract.
        return new Complex_Number((this.re()-input.re()),(this.im()-input.im()));
    }

    public Complex_Number mult(Complex_Number input){
        /*
            Multiply complex numbers by using FOIL.
            eg.  (a+ib)*(c+di)
                =ac + adi + bci + bdii
                =(ac-bd)+i(bc+ad)
         */
        double retRe = 0;
        double retIm = 0;

        retRe = this.re()*input.re() - this.im()*input.im();
        retIm = this.im()*input.re() + this.re()*input.im();

        return new Complex_Number(retRe,retIm);
    }

    public void setRe(double re){
        pRe = re;
    }

    public void setIm(double im){
        pIm = im;
    }

    public void set(double re, double im){
        pRe = re;
        pIm = im;
    }



}
