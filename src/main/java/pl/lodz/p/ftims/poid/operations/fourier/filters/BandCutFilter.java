package main.java.pl.lodz.p.ftims.poid.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.model.Complex;

/**
 * @author alisowsk
 */
public class BandCutFilter implements FourierFilter{
    private static int dMin;
    private static int dMax;

    public BandCutFilter(int dMin, int dMax){
        this.dMin = dMin;
        this.dMax = dMax;
    }

    public BandCutFilter() {
        this.dMin = 255;
        this.dMax = 0;
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
                    complexImage[m][n] = new Complex(0,0);
                }
            }
        }
    }
}
