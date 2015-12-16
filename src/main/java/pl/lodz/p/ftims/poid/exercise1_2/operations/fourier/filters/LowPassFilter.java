package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Complex;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.ImageConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alisowsk
 */
public class LowPassFilter implements FourierFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LowPassFilter.class);

    private int d;

    public LowPassFilter(int d){
        this.d = d;
    }

    public LowPassFilter() {
        this.d = ImageConstants.MIN_PIXEL_VALUE;
    }

    @Override
    public void applyFilter(Complex[][] complexImage) {

        int M = complexImage.length;
        int N = complexImage[0].length;


//        Complex[][] newComplexImage = new Complex[M][N];

        for(int m=0; m<M ; m++) {
            for(int n=0; n<N; n++) {
//                newComplexImage[m][n] = complexImage[m][n];
                if((Math.sqrt(Math.pow((m - M/2), 2) + Math.pow((n - N/2), 2))) > d ){
                    complexImage[m][n] = new Complex(0d, 0d);
                }
//                complexImage[m][n].setReal(complexImage[m][n].getReal() * (0.54 - 0.46 * Math.cos((2 * Math.PI * n) / (N-1))));
//                complexImage[m][n].setImaginary(complexImage[m][n].getImaginary() * (0.54 - 0.46 * Math.cos((2 * Math.PI * n) / (N-1))));
//                complexImage[m][n].setImaginary(complexImage[m][n].getImaginary() * (0.54 - 0.46 * Math.cos((2*Math.PI*n)/N)));
            }
        }

//        for(int m=0; m<M ; m++) {
//            for(int n=0; n<N; n++) {
//                newComplexImage[m][n].setReal(newComplexImage[m][n].getReal() * (0.54 - 0.46 * Math.cos((2*Math.PI*n)/(N-1))));
////                newComplexImage[m][n].setImaginary(newComplexImage[m][n].getImaginary() * (0.54 - 0.46 * Math.cos((2*Math.PI*n)/N)));
//            }
//        }
//
//        for(int m=0; m<M ; m++) {
//            for(int n=0; n<N; n++) {
//                complexImage[m][n] = complexImage[m][n].times(newComplexImage[m][n]);
//            }
//        }

    }

    @Override
    public void setMaxOrL(int maxOrL) {
        this.d = maxOrL;
    }
}
