package main.java.pl.lodz.p.ftims.poid.operations.histogram;

import main.java.pl.lodz.p.ftims.poid.model.Histogram;
import main.java.pl.lodz.p.ftims.poid.model.Image;

/**
 * @author alisowsk
 */
public class HyperbolicFinalProbDensFunction extends AbstractFinalProbDensFunction {
    public HyperbolicFinalProbDensFunction() {

    }

    @Override
    protected int processSingleColor(Image img, int x, int y, Histogram histogram) {
        int pixelsNum = img.getHeight() * img.getWidth();
        int f = img.getPixel(x,y).getColor(histogram.getColor());
        int sum = calculateSum(f, histogram);

        return (int) (gMin * Math.pow(((double)gMax / gMin), (double)sum / pixelsNum));
    }
}