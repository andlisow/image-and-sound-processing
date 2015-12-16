package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.histogram;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Histogram;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.ImageConstants;

/**
 * @author alisowsk
 */
public class RaleighFinalProbDensFunction extends AbstractFinalProbDensFunction {
    public RaleighFinalProbDensFunction() {
        this.gMax = ImageConstants.MAX_PIXEL_VALUE;
    }

    @Override
    protected int processSingleColor(Image img, int x, int y, Histogram histogram) {
        int N = img.getHeight() * img.getWidth();
        double alpha = (gMax - gMin) / Math.sqrt(2 * Math.log(N));
        int f = img.getPixel(x,y).getColor(histogram.getColor());
        int sum = calculateSum(f, histogram);
        int result = (int) (gMin + Math.sqrt(2 * Math.pow(alpha, 2) * Math.log(1 /((double) sum / N))));
        if(result > ImageConstants.MAX_PIXEL_VALUE){
            return ImageConstants.MAX_PIXEL_VALUE;
        }
        return result;
    }

}