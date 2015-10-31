package main.java.pl.lodz.p.ftims.poid.operations.basic;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
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
        for(RgbColor color : RgbColor.values()){
            pixel.setColor(color, MAX_PIXEL_VALUE - pixel.getColor(color));
        }
    }

}
