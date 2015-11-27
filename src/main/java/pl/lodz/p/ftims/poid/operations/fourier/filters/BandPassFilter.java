package main.java.pl.lodz.p.ftims.poid.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.model.Complex;
import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.utils.ImageConstants;

/**
 * @author alisowsk
 */
public class BandPassFilter implements FourierFilter {
    private final int dMin;
    private final int dMax;

    public BandPassFilter(int dMin, int dMax){
        this.dMin = dMin;
        this.dMax = dMax;
    }

    public BandPassFilter() {
        this.dMin = ImageConstants.MIN_PIXEL_VALUE;
        this.dMax = ImageConstants.MAX_PIXEL_VALUE;
    }

    @Override
    public void applyFilter(Complex[][] complexImage) {
        int M = complexImage.length;
        int N = complexImage[0].length;

        double x;

        for(int m=0; m<M ; m++) {
            for(int n=0; n<N; n++) {
                x = Math.sqrt(Math.pow((m - M/2),2) + Math.pow((n - N/2),2));
                if((x > dMax) || ( x < dMin)){
                    complexImage[m][n] = new Complex(0d, 0d);
                }
            }
        }
    }
}
