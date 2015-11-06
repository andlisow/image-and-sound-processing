package main.java.pl.lodz.p.ftims.poid.operations.filters.nonlinear;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.operations.filters.AbstractFilter;
import main.java.pl.lodz.p.ftims.poid.utils.ImageConstants;

/**
 * @author alisowsk
 */
public class UolisOperator extends AbstractFilter {
    private static final int NORMALIZATION_COEFFICIENT = 10;
    public UolisOperator() {
    }

    @Override
    protected boolean checkIfNotBorderValue(Image img, int x, int y) {
        return x == 0 || y == 0 || x == img.getWidth()-1 || y == img.getHeight()-1;
    }

    @Override
    protected int processSingleColor(Image img, int x, int y, RgbColor c) {
        int a1 = img.getPixel(x,y-1).getColor(c);
        int a3 = img.getPixel(x+1,y).getColor(c);
        int a5 = img.getPixel(x,y+1).getColor(c);
        int a7 = img.getPixel(x-1,y).getColor(c);
        int result = (int) (NORMALIZATION_COEFFICIENT * 0.25*Math.log(Math.pow(img.getPixel(x,y).getColor(c),4)/(a1*a3*a5*a7)));
        if(result > ImageConstants.MAX_PIXEL_VALUE){
            return ImageConstants.MAX_PIXEL_VALUE;
        }
        return result;
    }
}
