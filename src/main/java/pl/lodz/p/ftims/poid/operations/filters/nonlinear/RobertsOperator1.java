package main.java.pl.lodz.p.ftims.poid.operations.filters.nonlinear;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;

/**
 * @author alisowsk
 */
public class RobertsOperator1 implements Transformable {
    public RobertsOperator1() {
    }

    @Override
    public Image process(Image originalImage) {
        Image result = new Image(originalImage);
        for(int x = 0; x<originalImage.getWidth()- 1; x++){
            for(int y = 0; y<originalImage.getHeight()- 1; y++){
                result.getPixels()[x][y] = processSinglePixel(originalImage, x, y);
            }
        }
        return result;
    }

    private Pixel processSinglePixel(Image originalImage, int x, int y) {
        Pixel pixel = new Pixel();
        for(Pixel.RgbColor color : Pixel.RgbColor.values()){
            int newColorValue = processSingleColor(originalImage, x, y, color);
            pixel.setColor(color, newColorValue);
        }
        return pixel;
    }

    // g(x,y) = ((f(x,y) - f(x+1,y+1))^2 + (f(x,y+1) - f(x+1,y))^2)^0.5
    private int processSingleColor(Image img, int x, int y, Pixel.RgbColor c) {
        return (int) Math.pow(
                    Math.pow(img.getPixel(x,y).getColor(c) - img.getPixel(x+1,y+1).getColor(c), 2)
                    + Math.pow(img.getPixel(x,y+1).getColor(c) - img.getPixel(x+1,y).getColor(c), 2)
                , 0.5);
    }
}
