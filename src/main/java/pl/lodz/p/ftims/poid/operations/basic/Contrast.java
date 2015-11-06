package main.java.pl.lodz.p.ftims.poid.operations.basic;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static main.java.pl.lodz.p.ftims.poid.utils.ImageConstants.*;

/**
 * @author alisowsk
 */
public class Contrast implements Transformable {
    private final float factor;

    public Contrast(float factor) {
        this.factor = factor;
    }

    @Override
    public Image process(Image image) {
        for (int x=0; x<image.getWidth(); x++){
            for (int y=0; y<image.getHeight(); y++){
                processSinglePixel(image.getPixel(x,y));
            }
        }
        return image;
    }

    private void processSinglePixel(Pixel pixel) {
        for(RgbColor color : RgbColor.values()){
            pixel.setColor(color, processSingleColor(pixel.getColor(color)));
        }
    }

    private int processSingleColor(int colorVal) {
        if(colorVal == 0){
            colorVal = 1;
        }
        if (colorVal >= MIDDLE_PIXEL_VALUE) {
            return getNewValue(colorVal, colorVal * factor);
        }
        return getNewValue(colorVal, colorVal / factor);
    }

    private int getNewValue(int oldValue, float newValue){
        if(oldValue >= MIDDLE_PIXEL_VALUE && newValue <= MIDDLE_PIXEL_VALUE && factor < 1){
            return MIDDLE_PIXEL_VALUE;
        } else if(oldValue < MIDDLE_PIXEL_VALUE && newValue > MIDDLE_PIXEL_VALUE && factor < 1){
            return MIDDLE_PIXEL_VALUE;
        } else if (newValue > MAX_PIXEL_VALUE){
            return MAX_PIXEL_VALUE;
        }
        return (int) newValue;
    }
}
