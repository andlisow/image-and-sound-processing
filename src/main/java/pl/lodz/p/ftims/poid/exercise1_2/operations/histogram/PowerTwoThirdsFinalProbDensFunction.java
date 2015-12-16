package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.histogram;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Histogram;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;

/**
 * @author alisowsk
 */
public class PowerTwoThirdsFinalProbDensFunction extends AbstractFinalProbDensFunction {
    public PowerTwoThirdsFinalProbDensFunction() {
    }

    @Override
    protected int processSingleColor(Image img, int x, int y, Histogram histogram) {
        double gMinToOneThird = Math.pow(gMin, (double)1/3);
        double gMaxToOneThird = Math.pow(gMax, (double)1/3);
        int f=img.getPixel(x,y).getColor(histogram.getColor());
        int pixelsNum = img.getHeight() * img.getWidth();
        int sum = calculateSum(f, histogram);

        return (int) Math.pow(gMinToOneThird + (gMaxToOneThird - gMinToOneThird) * ((double)sum / pixelsNum), 3);
    }

}