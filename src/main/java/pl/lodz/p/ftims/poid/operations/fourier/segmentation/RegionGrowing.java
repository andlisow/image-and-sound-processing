package main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author alisowsk
 */
public class RegionGrowing implements RegionSegmentation {
    private static final Logger LOG = LoggerFactory.getLogger(RegionGrowing.class);

    private static final int SEED_NUM_ROWS = 8;
    private static final int SEED_NUM_COLS = 8;
    private static final int SEED_NUM = SEED_NUM_COLS * SEED_NUM_ROWS;

    private int threshold;
    private int minimumPixelsForRegion;

    public RegionGrowing(){
        this.threshold = 10;
        this.minimumPixelsForRegion = 20;
    }

    public RegionGrowing(int threshold, int minimumPixelsForRegion){
        this.threshold = threshold;
        this.minimumPixelsForRegion = minimumPixelsForRegion;
    }

    @Override
    public Image process(Image original) {
        int width = original.getWidth();
        int height = original.getHeight();

        Image regionsMask = new Image(original.getName(), width, height);
        Image result = new Image(original);

        PixelPoint[] seedPoints = prepareSeedPoints(original);

        runRegionGrowing(original, regionsMask, seedPoints);
        applyMaskToResultImage(regionsMask, result);

        return result;
    }

    private PixelPoint[] prepareSeedPoints(Image image) {
        int width = image.getWidth();
        int height = image.getHeight();

        PixelPoint[] seedPoints = new PixelPoint[SEED_NUM];
        for(int x=0; x< SEED_NUM_ROWS; x++){
            for(int y=0; y< SEED_NUM_COLS; y++){
                seedPoints[x * SEED_NUM_ROWS + y] = new PixelPoint(image.getPixel(x*(width/SEED_NUM_ROWS), y*(height/SEED_NUM_COLS)), x*(width/SEED_NUM_ROWS), y*(height/SEED_NUM_COLS));
            }
        }

        return seedPoints;
    }

    private void runRegionGrowing(Image original, Image regionsMask, PixelPoint[] seeds) {
        int width = original.getWidth();
        int height = original.getHeight();
        int regions =0;
        for(PixelPoint seed : seeds){
            int min = ((seed.pixel.getGrayScale() - threshold) > 0) ? seed.pixel.getGrayScale() - threshold : 0;
            int max = ((seed.pixel.getGrayScale() + threshold) < 255) ? seed.pixel.getGrayScale() + threshold : 255;

            Stack<PixelPoint> stack = new Stack<>();
            List<PixelPoint> region = new ArrayList<>();

            if(regionsMask.getPixel(seed.x,seed.y).getGrayScale() < 0) { //meaning it was not examined yet
                stack.push(seed);

                while (!stack.isEmpty()) {
                    PixelPoint curFromStack = stack.pop();
                    for (int x = -1; x < 2; x++) {
                        for (int y = -1; y < 2; y++) {
                            if (curFromStack.x+x >= width || curFromStack.x+x <= 0 || curFromStack.y+y >= height || curFromStack.y+y <= 0) {
                                continue;
                            }
                            if (regionsMask.getPixel(curFromStack.x+x, curFromStack.y+y).getGrayScale() >= 0) {
                                continue;
                            }

                            PixelPoint curFromNeighbourhood = new PixelPoint(regionsMask.getPixel(curFromStack.x+x, curFromStack.y+y), curFromStack.x+x, curFromStack.y+y);

                            if (original.getPixel(curFromStack.x+x, curFromStack.y+y).getGrayScale() <= max
                                    && original.getPixel(curFromStack.x+x, curFromStack.y+y).getGrayScale() >= min) {
                                for (RgbColor color : RgbColor.values()){
                                    regionsMask.getPixel(curFromStack.x+x, curFromStack.y+y).setColor(color, Math.abs(255 - 15*regions));
                                }
                                region.add(curFromNeighbourhood);
                                stack.push(curFromNeighbourhood);
                            }
                        }
                    }
                }

                if(region.size() < minimumPixelsForRegion){
                    for(PixelPoint point : region){
                        for (Pixel.RgbColor color : Pixel.RgbColor.values()) {
                            regionsMask.getPixel(point.x, point.y).setColor(color, -10);
                        }
                    }
                } else {
                    regions++;
                }

                region.clear();
            }
        }

    }

    private void applyMaskToResultImage(Image imageWithRegions, Image result) {
        int width = result.getWidth();
        int height = result.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(imageWithRegions.getPixel(x,y).getGrayScale() >= 0){
                    result.getPixel(x, y).setRed(0);
                    result.getPixel(x, y).setGreen(imageWithRegions.getPixel(x,y).getGreen());
                    result.getPixel(x, y).setBlue(imageWithRegions.getPixel(x,y).getBlue());
                }
            }
        }
    }

    @Override
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void setMinimumPixelsForRegion(int minimumPixelsForRegion) {
        this.minimumPixelsForRegion = minimumPixelsForRegion;
    }

    private class PixelPoint {
        private Pixel pixel;
        private int x;
        private int y;

        public PixelPoint(Pixel pixel, int x, int y) {
            this.pixel = pixel;
            this.x = x;
            this.y = y;
        }
    }

}
