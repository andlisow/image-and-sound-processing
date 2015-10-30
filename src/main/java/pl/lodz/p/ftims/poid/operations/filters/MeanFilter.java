package main.java.pl.lodz.p.ftims.poid.operations.filters;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alisowsk
 */
public class MeanFilter implements Transformable {
    private static final Logger LOG = LoggerFactory.getLogger(MeanFilter.class);

    private final int maskSize;
    private final int borderSize;

    public MeanFilter(int maskSize) {
        this.maskSize = maskSize;
        this.borderSize = (int) ((Math.sqrt(maskSize)-1)/2);
    }

    @Override
    public Image process(Image originalImage) {
        Image result = new Image(originalImage);
        for(int x = borderSize; x<originalImage.getWidth()- borderSize; x++){
            for(int y = borderSize; y<originalImage.getHeight()- borderSize; y++){
                result.getPixels()[x][y] = processSinglePixel(originalImage, x, y);
            }
        }
        return result;
    }

    private Pixel processSinglePixel(Image originalImage, int width, int height) {
        Pixel pixel = new Pixel();
        for(RgbColor color : RgbColor.values()){
            int newColorValue = processSingleColor(originalImage, width, height, color);
            pixel.setColor(color, newColorValue);
        }
        return pixel;
    }

    private int processSingleColor(Image originalImage, int width, int height, RgbColor color) {
        int colorVal=0;
        for(int x=width-borderSize; x<=width+borderSize;x++){
            for(int y=height-borderSize; y<=height+borderSize; y++){
                colorVal += originalImage.getPixels()[x][y].getColor(color);
            }
        }
        return colorVal / maskSize;
    }
}
