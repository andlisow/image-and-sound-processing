package main.java.pl.lodz.p.ftims.poid.operations.basic;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import static main.java.pl.lodz.p.ftims.poid.utils.ImageConstants.*;

/**
 * @author alisowsk
 */
public class Negative implements Transformable {

    @Override
    public Image process(Image image){
        for (int x=0; x<image.getWidth(); x++){
            for (int y=0; y<image.getHeight(); y++){
                processSinglePixel(image.getPixels()[x][y]);
            }
        }
        return image;
    }

    private void processSinglePixel(Pixel pixel) {
        pixel.setRed(MAX_PIXEL_VALUE - pixel.getRed());
        pixel.setGreen(MAX_PIXEL_VALUE - pixel.getGreen());
        pixel.setBlue(MAX_PIXEL_VALUE - pixel.getBlue());
    }

}
