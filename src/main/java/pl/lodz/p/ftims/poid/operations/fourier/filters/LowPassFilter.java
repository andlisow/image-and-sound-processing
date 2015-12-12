package main.java.pl.lodz.p.ftims.poid.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.model.Complex;
import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.utils.ImageConstants;
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
LOG.error("d" + d);


        int M = complexImage.length;
        int N = complexImage[0].length;

        for(int m=0; m<M ; m++) {
            for(int n=0; n<N; n++) {
                if((Math.sqrt(Math.pow((m - M/2), 2) + Math.pow((n - N/2), 2))) > d ){
                    complexImage[m][n] = new Complex(0d, 0d);
                }
            }
        }
    }

    @Override
    public void setMaxOrL(int maxOrL) {
        this.d = maxOrL;
    }
}
