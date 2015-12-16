package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.histogram;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Histogram;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;

/**
 * @author alisowsk
 */
public class ExponentialFinalProbDensFunction extends AbstractFinalProbDensFunction {
    public ExponentialFinalProbDensFunction() {
    }

    @Override
    protected int processSingleColor(Image img, int x, int y, Histogram histogram) {
        double eps = 0.0001;
        double alpha = -((double)(gMax - gMin) / Math.log(eps));
        int pixelsNum = img.getHeight() * img.getWidth();
        int f = img.getPixel(x,y).getColor(histogram.getColor());
        int sum = calculateSum(f, histogram);
//TODO does not work... alpha?
        return (int) (gMin - 1 / alpha * Math.log(1 - (double)sum / pixelsNum));
    }
}
