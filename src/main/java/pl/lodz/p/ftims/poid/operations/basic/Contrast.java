package main.java.pl.lodz.p.ftims.poid.operations.basic;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;

import static main.java.pl.lodz.p.ftims.poid.utils.ImageConstants.MAX_PIXEL_VALUE;
import static main.java.pl.lodz.p.ftims.poid.utils.ImageConstants.MIDDLE_PIXEL_VALUE;

/**
 * @author alisowsk
 */
public class Contrast implements Transformable {
    private final float coefficient;

    public Contrast(float coefficient) {
        this.coefficient = coefficient;
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
        float temp = coefficient * (colorVal - MIDDLE_PIXEL_VALUE) + MIDDLE_PIXEL_VALUE;
        if (temp < 0) {
            return 0;
        } else if((0 <= temp) && (temp <= MAX_PIXEL_VALUE)) {
            return (int) temp;
        }
        return MAX_PIXEL_VALUE;
    }

}
