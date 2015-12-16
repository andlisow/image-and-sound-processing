package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.nonlinear;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.AbstractFilter;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.ImageConstants;

/**
 * @author alisowsk
 */
public class RosenfeldOperator extends AbstractFilter {
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
        int result = (sum1 - sum2)/rCoefficient;
        if(result > ImageConstants.MAX_PIXEL_VALUE){
            return ImageConstants.MAX_PIXEL_VALUE;
        }
        return result;
    }

    public void setrCoefficient(int rCoefficient) {
        this.rCoefficient = rCoefficient;
    }
}
