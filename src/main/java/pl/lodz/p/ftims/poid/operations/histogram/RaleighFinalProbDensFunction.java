package main.java.pl.lodz.p.ftims.poid.operations.histogram;

import main.java.pl.lodz.p.ftims.poid.model.Histogram;
import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.utils.ImageConstants;

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

        return (int) (gMin + Math.pow(2 * Math.pow(alpha, 2) * Math.pow(Math.log((double)sum / N), -1), (double)1/2));
    }

}