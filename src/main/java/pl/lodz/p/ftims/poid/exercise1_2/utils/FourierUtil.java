package main.java.pl.lodz.p.ftims.poid.exercise1_2.utils;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Complex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alisowsk
 */
public class FourierUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FourierUtil.class);

    private FourierUtil(){

    }

    public static Complex[][] fftDit2d(Complex[][] complex2d) {
        final int rows = complex2d.length;
        final int cols = complex2d[0].length;

        Complex[][] afterRowTransformComplex = new Complex[rows][cols];
        Complex[][] afterColTransformComplex = new Complex[rows][cols];

        for(int x=0; x<rows; x++){
            Complex[] complex = complex2d[x];
            afterRowTransformComplex[x] = fftDit1d(complex);
        }

        afterRowTransformComplex = swap2dDimensions(afterRowTransformComplex);

        for(int x=0; x<rows; x++){
            Complex[] complex = afterRowTransformComplex[x];
            afterColTransformComplex[x] = fftDit1d(complex);
        }

        return afterColTransformComplex;
    }

    public static Complex[][] iFftDit2d(Complex[][] complex2d) {
        final int rows = complex2d.length;
        final int cols = complex2d[0].length;

        Complex[][] afterRowTransformComplex = new Complex[rows][cols];
        Complex[][] afterColTransformComplex = new Complex[rows][cols];

        for(int x=0; x<rows; x++){
            Complex[] complex = complex2d[x];
            afterRowTransformComplex[x] = iFftDit1d(complex);
        }

        afterRowTransformComplex = swap2dDimensions(afterRowTransformComplex);

        for(int x=0; x<rows; x++){
            Complex[] complex = afterRowTransformComplex[x];
            afterColTransformComplex[x] = iFftDit1d(complex);
        }

        return afterColTransformComplex;
    }

    public static Complex[] fftDit1d(Complex[] complex){
        final int N = complex.length;

        // base case
        if (N == 1){
            return new Complex[] { complex[0] };
        }

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) {
            LOG.error("N has to be the power of 2");
        }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = complex[2*k];
        }
        Complex[] q = fftDit1d(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < N/2; k++) {
            odd[k] = complex[2*k + 1];
        }
        Complex[] r = fftDit1d(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N/2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k] = q[k].plus(wk.times(r[k]));
            y[k + N/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }

    public static Complex[] iFftDit1d(Complex[] complex){
        final int N = complex.length;
        Complex[] y = new Complex[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = complex[i].conjugate();
        }

        // compute forward FFT
        y = fftDit1d(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].times(1.0 / N);
        }

        return y;
    }

    public static Complex[][] swap2dDimensions(Complex[][] complex) {
        final int rows = complex.length;
        final int cols = complex[0].length;

        Complex[][] swappedTab = new Complex[rows][cols];
        for(int x=0; x<rows; x++){
            for(int y=0; y<cols; y++){
                swappedTab[x][y] = complex[y][x];
            }
        }

        return swappedTab;
    }

}
