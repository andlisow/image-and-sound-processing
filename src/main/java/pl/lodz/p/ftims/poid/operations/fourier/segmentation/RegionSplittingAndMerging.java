package main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation.tree.PixelPoint;
import main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation.tree.QuadTree;
import main.java.pl.lodz.p.ftims.poid.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author alisowsk
 */
public class RegionSplittingAndMerging implements RegionSegmentation {
    private static final Logger LOG = LoggerFactory.getLogger(RegionSplittingAndMerging.class);

    private int threshold;
    private int minimumPixelsForRegion;

    @Override
    public Image process(Image originalImage) {
        int size = originalImage.getHeight();
        Image resultImage = new Image(originalImage);
        Image imageWithRegionNumbers = new Image(originalImage.getName(), size, size);

        PixelPoint[][] pixelPoints = new PixelPoint[size][size];

        for (RgbColor color : RgbColor.values()){
            for(int x=0; x<size; x++){
                for(int y=0; y<size; y++){
                    resultImage.getPixel(x, y).setColor(color, ImageUtil.getGrayScaleValue(resultImage.getPixel(x, y)));
                    pixelPoints[x][y] = new PixelPoint(ImageUtil.getGrayScaleValue(resultImage.getPixel(x, y)),x,y);
                }
            }
        }

        QuadTree tree = new QuadTree(pixelPoints);
        tree.process(threshold);

        List<PixelPoint[][]> regions = tree.getRegions();
        TreeMap<Integer, PixelPoint[][]> regionsMap = new TreeMap<>();
        for(int i=0; i<regions.size(); i++){
            PixelPoint[][] region = regions.get(i);
            for(int x=0; x<region.length; x++){
                for(int y=0; y<region[0].length; y++) {
                    imageWithRegionNumbers.getPixel(region[x][y].getX(), region[x][y].getY()).setColor(RgbColor.RED, i);
                    imageWithRegionNumbers.getPixel(region[x][y].getX(), region[x][y].getY()).setColor(RgbColor.GREEN, i);
                    imageWithRegionNumbers.getPixel(region[x][y].getX(), region[x][y].getY()).setColor(RgbColor.BLUE, i);
                }
            }
            regionsMap.put(i, region);
        }

        Image regionsMask = new Image(originalImage.getName(), size, size);
        Image finalMask = new Image(originalImage.getName(), size, size);
        while(!regionsMap.isEmpty()){
            Integer curKey = regionsMap.firstEntry().getKey();
            PixelPoint[][] cur = regionsMap.get(curKey);
            Stack<Integer> regionNumbers = new Stack<>();
            Image finalMask2 = new Image(originalImage.getName(), size, size);
            regionNumbers.add(curKey);
            int regionElements = 0;
            int curMin=-10;
            int curMax=-10;
            while (cur!=null){

                for(int x=0; x<cur.length; x++){
                    for(int y=0; y<cur.length; y++){
                        regionsMask.getPixel(cur[x][y].getX(),cur[x][y].getY()).setColor(RgbColor.RED, cur[x][y].getValue());
                        regionsMask.getPixel(cur[x][y].getX(),cur[x][y].getY()).setColor(RgbColor.GREEN, cur[x][y].getValue());
                        regionsMask.getPixel(cur[x][y].getX(),cur[x][y].getY()).setColor(RgbColor.BLUE, cur[x][y].getValue());
                        regionElements++;
                        finalMask2.getPixel(cur[x][y].getX(),cur[x][y].getY()).setColor(RgbColor.RED, cur[x][y].getValue());
                        finalMask2.getPixel(cur[x][y].getX(),cur[x][y].getY()).setColor(RgbColor.GREEN, cur[x][y].getValue());
                        finalMask2.getPixel(cur[x][y].getX(),cur[x][y].getY()).setColor(RgbColor.BLUE, cur[x][y].getValue());
                    }
                }
                Set<Integer> adjacentRegions = new HashSet<>();
                for(int x=1; x<regionsMask.getWidth()-1; x++){
                    for(int y=1; y<regionsMask.getHeight()-1; y++){
                        if(((regionsMask.getPixel(x,y).getGray() == -10 && regionsMask.getPixel(x+1,y).getGray() != -10))
                                ||((regionsMask.getPixel(x,y).getGray() == -10 && regionsMask.getPixel(x-1,y).getGray() != -10))
                                ||((regionsMask.getPixel(x,y).getGray() == -10 && regionsMask.getPixel(x,y+1).getGray() != -10))
                                ||((regionsMask.getPixel(x,y).getGray() == -10 && regionsMask.getPixel(x,y-1).getGray() != -10))){
                            int regionNumber = imageWithRegionNumbers.getPixel(x,y).getGray();
                            adjacentRegions.add(regionNumber);
                        }
                    }
                }

                if(curMin < 0){
                    curMin = getMin(cur);
                }
                if(curMax < 0){
                    curMax = getMax(cur);
                }

                for(Integer key : adjacentRegions){
                    PixelPoint[][] region = regionsMap.get(key);
                    if(region!=null){
                        int regionMin = getMin(region);
                        int regionMax = getMax(region);

                        int newMin = (regionMin < curMin) ? regionMin : curMin;
                        int newMax = (regionMax > curMax) ? regionMax : curMax;

                        if(Math.abs(newMax - newMin) <= threshold){
                            for(int x=0; x<region.length; x++){
                                for(int y=0; y<region.length; y++){
                                    regionsMask.getPixel(region[x][y].getX(),region[x][y].getY()).setColor(RgbColor.RED, region[x][y].getValue());
                                    regionsMask.getPixel(region[x][y].getX(),region[x][y].getY()).setColor(RgbColor.GREEN, region[x][y].getValue());
                                    regionsMask.getPixel(region[x][y].getX(),region[x][y].getY()).setColor(RgbColor.BLUE, region[x][y].getValue());
                                    regionElements++;
                                    finalMask2.getPixel(region[x][y].getX(),region[x][y].getY()).setColor(RgbColor.RED, region[x][y].getValue());
                                    finalMask2.getPixel(region[x][y].getX(),region[x][y].getY()).setColor(RgbColor.GREEN, region[x][y].getValue());
                                    finalMask2.getPixel(region[x][y].getX(),region[x][y].getY()).setColor(RgbColor.BLUE, region[x][y].getValue());
                                }
                            }
                            if(!regionNumbers.contains(key))
                            regionNumbers.add(key);
                        }
                    }
                }

                regionsMap.remove(curKey);
                curKey = regionNumbers.pop();
                cur = regionsMap.get(curKey);
            }
            if(regionElements >= minimumPixelsForRegion){
                for (int i = 0; i < finalMask2.getHeight(); i++) {
                        for (int j = 0; j < finalMask2.getWidth(); j++) {
                            if(finalMask2.getPixel(i,j).getGray() >= 0) {
                                finalMask.getPixel(i,j).setColor(RgbColor.RED, 0);
                                finalMask.getPixel(i,j).setColor(RgbColor.GREEN, 255);
                                finalMask.getPixel(i,j).setColor(RgbColor.BLUE, 255);
                            }
                        }
                    }
                }
        }

        return finalMask;
    }

    private int getMin(PixelPoint[][] pixelPoints) {
        int min = pixelPoints[0][0].getValue();
        for(int i=0; i<pixelPoints.length; i++){
            for(int j=0; j<pixelPoints[0].length; j++){
                int curVal = pixelPoints[i][j].getValue();
                if(min > curVal){
                    min = curVal;
                }
            }
        }
        return min;
    }

    private int getMax(PixelPoint[][] pixelPoints) {
        int max = pixelPoints[0][0].getValue();
        for(int i=0; i<pixelPoints.length; i++){
            for(int j=0; j<pixelPoints[0].length; j++){
                int curVal = pixelPoints[i][j].getValue();
                if(max < curVal){
                    max = curVal;
                }
            }
        }
        return max;
    }

    @Override
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void setMinimumPixelsForRegion(int minimumPixelsForRegion) {
        this.minimumPixelsForRegion = minimumPixelsForRegion;
    }
}
