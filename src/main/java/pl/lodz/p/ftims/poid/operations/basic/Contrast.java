package main.java.pl.lodz.p.ftims.poid.operations.basic;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import static main.java.pl.lodz.p.ftims.poid.utils.ImageConstants.*;

/**
 * @author alisowsk
 */
public class Contrast implements Transformable {
    private static final int MIDDLE_PIXEL_VALUE = 128;

    private final float factor;

    public Contrast(float factor) {
        this.factor = factor;
    }

    @Override
    public Image process(Image image) {
        for (int x=0; x<image.getWidth(); x++){
            for (int y=0; y<image.getHeight(); y++){
                processSinglePixel(image.getPixels()[x][y]);
            }
        }
        return image;
    }

    private void processSinglePixel(Pixel pixel) {
        pixel.setRed(processSingleColor(pixel.getRed()));
        pixel.setGreen(processSingleColor(pixel.getGreen()));
        pixel.setBlue(processSingleColor(pixel.getBlue()));
    }

    private int processSingleColor(int colorVal) {
        if (colorVal >= MIDDLE_PIXEL_VALUE) {
            getNewValue(colorVal * factor);
        }
        return getNewValue(colorVal / factor);
    }

    private int getNewValue(float newValue){
        if(newValue < MIN_PIXEL_VALUE){
            return MIN_PIXEL_VALUE;
        } else if (newValue > MAX_PIXEL_VALUE){
            return MAX_PIXEL_VALUE;
        }
        return (int) newValue;
    }
}
