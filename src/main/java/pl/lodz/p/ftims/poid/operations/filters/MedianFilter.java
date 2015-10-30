package main.java.pl.lodz.p.ftims.poid.operations.filters;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;

import java.util.Arrays;

/**
 * @author alisowsk
 */
public class MedianFilter implements Transformable {
    private final int maskSize;
    private final int borderSize;

    public MedianFilter(int maskSize) {
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
        for(Pixel.RgbColor color : Pixel.RgbColor.values()){
            int newColorValue = processSingleColor(originalImage, width, height, color);
            pixel.setColor(color, newColorValue);
        }
        return pixel;
    }

    private int processSingleColor(Image originalImage, int width, int height, Pixel.RgbColor color) {
        int[] colorValues = new int[maskSize];
        int index=0;
        for(int x=width-borderSize; x<=width+borderSize;x++){
            for(int y=height-borderSize; y<=height+borderSize; y++){
                colorValues[index++] = originalImage.getPixels()[x][y].getColor(color);
            }
        }
        Arrays.sort(colorValues);
        return getMedian(colorValues);
    }

    private int getMedian(int[] table){
        int middle = table.length/2;
        return (table.length % 2 == 1) ? table[middle] : (table[middle-1] + table[middle]) / 2;
    }

}
