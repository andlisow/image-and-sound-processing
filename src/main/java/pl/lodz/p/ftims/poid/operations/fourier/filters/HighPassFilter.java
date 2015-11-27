package main.java.pl.lodz.p.ftims.poid.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.model.Complex;
import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.utils.ImageConstants;

/**
 * @author alisowsk
 */
public class HighPassFilter implements FourierFilter {
    private final int d;

    public HighPassFilter(int d){
        this.d = d;
    }

    public HighPassFilter() {
        this.d = ImageConstants.MIN_PIXEL_VALUE;
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
}
