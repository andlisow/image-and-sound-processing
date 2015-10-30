package main.java.pl.lodz.p.ftims.poid.operations.filters.nonlinear;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.operations.filters.AbstractFilter;

/**
 * @author alisowsk
 */
public class RobertsOperator2 extends AbstractFilter {
    public RobertsOperator2() {
    }

    @Override
    protected boolean checkIfNotBorderValue(Image img, int x, int y) {
        return x == img.getWidth()-1 || y == img.getHeight()-1;
    }

    @Override
    protected int processSingleColor(Image img, int x, int y, RgbColor c) {
        return Math.abs(img.getPixel(x,y).getColor(c) - img.getPixel(x+1,y+1).getColor(c))
               + Math.abs(img.getPixel(x,y+1).getColor(c) - img.getPixel(x+1,y).getColor(c));
    }
}
