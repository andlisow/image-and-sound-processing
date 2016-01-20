package main.java.pl.lodz.p.ftims.poid.exercise3.utils;

import main.java.pl.lodz.p.ftims.poid.exercise3.model.Complex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alisowsk
 */
public class FourierTransformUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FourierTransformUtil.class);

    private FourierTransformUtil(){

    }

    public static Complex[] dif1d(Complex[] x) {
        int N = x.length;

        // base case
        if (N == 1){
            return new Complex[] { x[0] };
        }

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) {
            LOG.error("N has to be the power of 2");
        }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = dif1d(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = dif1d(odd);

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

}
