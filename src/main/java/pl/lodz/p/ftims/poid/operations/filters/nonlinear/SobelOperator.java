package main.java.pl.lodz.p.ftims.poid.operations.filters.nonlinear;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;

/**
 * @author alisowsk
 */
public class SobelOperator extends AbstractNonLinearOperator {
    public SobelOperator() {
    }

    @Override
    protected boolean checkIfNotBorderValue(Image img, int x, int y) {
        return x == 0 || y == 0 || x == img.getWidth()-1 || y == img.getHeight()-1;
    }

    @Override
    protected int processSingleColor(Image img, int x, int y, RgbColor c) {
        int[] a = calculateA(img,x,y,c);
        int sx = (a[2] + 2*a[3] + a[4]) - (a[0] + 2*a[7] + a[6]);
        int sy = (a[0] + 2*a[1] + a[2]) - (a[6] + 2*a[5] + a[4]);
        return (int) Math.sqrt(Math.pow(sx,2) + Math.pow(sy, 2));
    }

    private int[] calculateA(Image img, int x, int y, RgbColor c) {
        return new int[]{
                img.getPixel(x - 1, y - 1).getColor(c),
                img.getPixel(x, y - 1).getColor(c),
                img.getPixel(x + 1, y - 1).getColor(c),
                img.getPixel(x + 1, y).getColor(c),
                img.getPixel(x + 1, y + 1).getColor(c),
                img.getPixel(x, y + 1).getColor(c),
                img.getPixel(x - 1, y + 1).getColor(c),
                img.getPixel(x - 1, y).getColor(c)
        };
    }
}
