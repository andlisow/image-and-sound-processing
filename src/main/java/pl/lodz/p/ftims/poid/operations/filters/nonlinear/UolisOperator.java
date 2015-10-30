package main.java.pl.lodz.p.ftims.poid.operations.filters.nonlinear;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;

/**
 * @author alisowsk
 */
public class UolisOperator extends AbstractNonLinearOperator {
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
        return (int) (0.25*Math.log(Math.pow(img.getPixel(x,y).getColor(c),4)/(a1*a3*a5*a7)));
    }
}
