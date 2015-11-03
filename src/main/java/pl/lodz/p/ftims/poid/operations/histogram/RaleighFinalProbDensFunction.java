package main.java.pl.lodz.p.ftims.poid.operations.histogram;

import main.java.pl.lodz.p.ftims.poid.model.Histogram;
import main.java.pl.lodz.p.ftims.poid.model.Image;

/**
 * @author alisowsk
 */
public class RaleighFinalProbDensFunction extends AbstractFinalProbDensFunction {
    public RaleighFinalProbDensFunction() {

    }

    @Override
    protected int processSingleColor(Image img, int x, int y, Histogram histogram) {
        double eps = 0.0001;
        double alpha = -((double)(gMax - gMin) / Math.log(eps));
        int N = img.getHeight() * img.getWidth();
        int f = img.getPixel(x,y).getColor(histogram.getColor());
        int sum = calculateSum(f, histogram);
//TODO does not work... alpha?
        return (int) (gMin + Math.pow(2 * Math.pow(alpha, 2) * Math.pow(Math.log((double)sum / N), -1), (double)1/2));
    }

}