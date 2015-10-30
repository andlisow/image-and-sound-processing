package main.java.pl.lodz.p.ftims.poid.operations.filters;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;

/**
 * @author alisowsk
 */
public abstract class AbstractFilter implements Transformable {
    public AbstractFilter() {
    }

    @Override
    public Image process(Image originalImage) {
        Image result = new Image(originalImage);
        for(int x = 0; x<originalImage.getWidth(); x++){
            for(int y = 0; y<originalImage.getHeight(); y++){
                if(!checkIfNotBorderValue(originalImage,x,y)){
                    result.getPixels()[x][y] = processSinglePixel(originalImage, x, y);
                }
            }
        }
        return result;
    }

    private Pixel processSinglePixel(Image originalImage, int x, int y) {
        Pixel pixel = new Pixel();
        for(RgbColor color : RgbColor.values()){
            int newColorValue = processSingleColor(originalImage, x, y, color);
            pixel.setColor(color, newColorValue);
        }
        return pixel;
    }

    protected abstract boolean checkIfNotBorderValue(Image img, int x, int y);

    protected abstract int processSingleColor(Image img, int x, int y, RgbColor c);
}
