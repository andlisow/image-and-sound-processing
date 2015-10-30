package main.java.pl.lodz.p.ftims.poid.operations.filters.nonlinear;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;

/**
 * @author alisowsk
 */
public class RosenfeldOperator extends AbstractNonLinearOperator {
    private int rCoefficient;

    public RosenfeldOperator() {
    }

    @Override
    protected boolean checkIfNotBorderValue(Image img, int x, int y) {
        return x < rCoefficient || x > img.getWidth() - rCoefficient;
    }

    @Override
    protected int processSingleColor(Image img, int x, int y, RgbColor c) {
        int sum1=0, sum2=0;
        for(int i=1; i<rCoefficient; i++){
            sum1 += img.getPixel(x+i-1,y).getColor(c);
        }
        for(int i=1; i<rCoefficient; i++){
            sum2 += img.getPixel(x-1,y).getColor(c);
        }
        return (sum1 - sum2)/rCoefficient;
    }

    public void setrCoefficient(int rCoefficient) {
        this.rCoefficient = rCoefficient;
    }
}
