package main.java.pl.lodz.p.ftims.poid.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.model.Complex;
import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;

/**
 * @author alisowsk
 */
public class BandPassFilter implements FourierFilter{
    private static int dMin;
    private static int dMax;

    public BandPassFilter(int dMin, int dMax){
        this.dMin = dMin;
        this.dMax = dMax;
    }

    public BandPassFilter() {
        this.dMin = 0;
        this.dMax = 255;
    }

    @Override
    public void applyFilter(Complex[][] complexImage) {
        int M = complexImage.length;
        int N = complexImage[0].length;

        double x;

        for(int m=0; m<M ; m++) {
            for(int n=0; n<N; n++) {
                x = Math.sqrt((m - M/2)*(m - M/2) + (n - N/2)*(n - N/2));
                if((x > dMax) || ( x < dMin)){
                    complexImage[m][n] = new Complex(0,0);
                }
            }
        }
    }
}
