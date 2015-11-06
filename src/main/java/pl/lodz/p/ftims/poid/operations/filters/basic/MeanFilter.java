package main.java.pl.lodz.p.ftims.poid.operations.filters.basic;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.operations.filters.AbstractFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alisowsk
 */
public class MeanFilter extends AbstractFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractFilter.class);
    private final int maskSize;
    private final int borderSize;

    public MeanFilter(int maskSize) {
        this.maskSize = maskSize;
        this.borderSize = (int) ((Math.sqrt(maskSize)-1)/2);
    }

    @Override
    protected boolean checkIfNotBorderValue(Image img, int x, int y) {
        return x < borderSize || x >= img.getWidth() - borderSize || y < borderSize || y >= img.getHeight() - borderSize;
    }

    protected int processSingleColor(Image originalImage, int width, int height, RgbColor color) {
        int colorVal=0;
        for(int x=width-borderSize; x<=width+borderSize;x++){
            for(int y=height-borderSize; y<=height+borderSize; y++){
               // LOG.error("" + x + " " + y);
                colorVal += originalImage.getPixel(x,y).getColor(color);
            }
        }
        return colorVal / maskSize;
    }
}
