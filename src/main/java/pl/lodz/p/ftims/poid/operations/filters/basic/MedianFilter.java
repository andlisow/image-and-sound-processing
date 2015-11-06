package main.java.pl.lodz.p.ftims.poid.operations.filters.basic;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.operations.filters.AbstractFilter;

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
        return x < borderSize || x > img.getWidth() - borderSize;
    }

    protected int processSingleColor(Image originalImage, int width, int height, Pixel.RgbColor color) {
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
        return (table[middle-1] + table[middle]) / 2;
    }

}
