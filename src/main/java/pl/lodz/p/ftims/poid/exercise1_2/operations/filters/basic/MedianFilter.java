package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.basic;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.AbstractFilter;

import java.util.Arrays;

/**
 * @author alisowsk
 */
public class MedianFilter extends AbstractFilter {
    private final int maskSize;
    private final int borderSize;

    public MedianFilter(int maskSize) {
        this.maskSize = maskSize;
        this.borderSize = (int) ((Math.sqrt(maskSize)-1)/2);
    }

    @Override
    protected boolean checkIfNotBorderValue(Image img, int x, int y) {
        return x < borderSize || x >= img.getWidth() - borderSize || y < borderSize || y >= img.getHeight() - borderSize;
    }

    protected int processSingleColor(Image originalImage, int width, int height, RgbColor color) {
        int[] colorValues = new int[maskSize];
        int index=0;
        for(int x=width-borderSize; x<=width+borderSize;x++){
            for(int y=height-borderSize; y<=height+borderSize; y++){
                colorValues[index++] = originalImage.getPixel(x,y).getColor(color);
            }
        }
        Arrays.sort(colorValues);
        return getMedian(colorValues);
    }

    private int getMedian(int[] table){
        int middle = table.length/2;
        return (table[middle-1] + table[middle]) / 2;
    }

}
