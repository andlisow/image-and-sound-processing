package main.java.pl.lodz.p.ftims.poid.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.model.Complex;
import main.java.pl.lodz.p.ftims.poid.utils.ImageConstants;

/**
 * @author alisowsk
 */
public class BandCutFilter implements FourierFilter {
    private int dMin;
    private int dMax;

    public BandCutFilter(int dMin, int dMax){
        this.dMin = dMin;
        this.dMax = dMax;
    }

    public BandCutFilter() {
        this.dMin = ImageConstants.MAX_PIXEL_VALUE;
        this.dMax = ImageConstants.MIN_PIXEL_VALUE;
    }

    @Override
    public void applyFilter(Complex[][] complexImage) {
        int M = complexImage.length;
        int N = complexImage[0].length;

        double x;

        for(int m=0; m<M ; m++) {
            for(int n=0; n<N; n++) {
                x = Math.sqrt((m - M/2)*(m - M/2) + (n - N/2)*(n - N/2));
                if( (x <= dMax) && ( x >= dMin) ){
                    complexImage[m][n] = new Complex(0d, 0d);
                }
            }
        }
    }

    @Override
    public void setMinOrK(int minOrK) {
        this.dMin = minOrK;
    }

    @Override
    public void setMaxOrL(int maxOrL) {
        this.dMax = maxOrL;
    }
}
