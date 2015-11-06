package main.java.pl.lodz.p.ftims.poid.operations.filters.linear;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.operations.filters.AbstractFilter;
import main.java.pl.lodz.p.ftims.poid.utils.ImageConstants;

/**
 * @author alisowsk
 */
public class LinearFilter extends AbstractFilter {
    private static final double NORMALIZATION_COEFFICIENT = 0.7;
    private int[][] mask;

    public LinearFilter(int[][] mask){
        this.mask = mask;
    }

    @Override
    protected boolean checkIfNotBorderValue(Image img, int x, int y) {
        return x == 0 || y == 0 || x == img.getWidth()-1 || y == img.getHeight()-1;
    }

    @Override
    protected int processSingleColor(Image img, int width, int height, RgbColor c) {
        int colorVal=0;
        for(int x=-1; x<2; x++){
            for(int y=-1; y<2; y++){
                colorVal += mask[x+1][y+1] * img.getPixel(width+x,height+y).getColor(c);
            }
        }
        int result = colorVal/9;
        if(result > ImageConstants.MAX_PIXEL_VALUE){
            return ImageConstants.MAX_PIXEL_VALUE;
        } else if (result < ImageConstants.MIN_PIXEL_VALUE){
            return ImageConstants.MIN_PIXEL_VALUE;
        }
        return (int) (result * NORMALIZATION_COEFFICIENT);
    }
}
