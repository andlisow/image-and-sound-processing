package main.java.pl.lodz.p.ftims.poid.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.model.Complex;
import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.utils.ImageConstants;

/**
 * @author alisowsk
 */
public class HighPassDetectionEdgeDirectionFilter implements FourierFilter {
    private int d;
    private Image mask;

    public HighPassDetectionEdgeDirectionFilter(int d){
        this.d = d;
    }

    public HighPassDetectionEdgeDirectionFilter() {
        this.d = ImageConstants.MAX_PIXEL_VALUE;
    }

    @Override
    public void applyFilter(Complex[][] complexImage) {
        int M = complexImage.length;
        int N = complexImage[0].length;

        Complex dc = complexImage[M/2][N/2];

        for(int m=0; m<M ; m++) {
            for(int n=0; n<N; n++) {
                if(mask.getPixel(m,n).getGray() == 0){
                    complexImage[m][n] = new Complex(0d,0d);
                } else if((Math.sqrt(Math.pow((m - M / 2), 2) + Math.pow((n - N / 2) , 2)) ) <= d ){
                    complexImage[m][n] = new Complex(0d,0d);
                }
            }
        }

        complexImage[M/2][N/2] = dc;
    }

    public void setMask(Image mask) {
        this.mask = mask;
    }

    @Override
    public void setMinOrK(int minOrK) {
        this.d = minOrK;
    }
}
