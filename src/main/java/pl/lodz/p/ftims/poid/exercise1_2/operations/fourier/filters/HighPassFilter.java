package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Complex;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.ImageConstants;

/**
 * @author alisowsk
 */
public class HighPassFilter implements FourierFilter {
    private int d;

    public HighPassFilter(int d){
        this.d = d;
    }

    public HighPassFilter() {
        this.d = ImageConstants.MAX_PIXEL_VALUE;
    }

    @Override
    public void applyFilter(Complex[][] complexImage) {
        int M = complexImage.length;
        int N = complexImage[0].length;

        Complex dc = complexImage[M/2][N/2];

        for(int m=0; m<M ; m++) {
            for(int n=0; n<N; n++) {
                if((Math.sqrt(Math.pow((m - M / 2), 2) + Math.pow((n - N / 2) , 2)) ) <= d ){
                    complexImage[m][n] = new Complex(0d,0d);
                }
            }
        }

        complexImage[M/2][N/2] = dc;
    }

    @Override
    public void setMinOrK(int minOrK) {
        this.d = minOrK;
    }
}
