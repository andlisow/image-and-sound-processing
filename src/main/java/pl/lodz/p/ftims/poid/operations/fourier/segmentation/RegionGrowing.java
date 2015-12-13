package main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author alisowsk
 */
public class RegionGrowing implements Transformable {
    private static final Logger LOG = LoggerFactory.getLogger(RegionGrowing.class);

    private static final int THRESHOLD = 100;
    private static final int MINIMUM_PIXELS_FOR_REGION = 20;
    private static final int SEED_NUM_ROWS = 8;
    private static final int SEED_NUM_COLS = 8;
    private static final int SEED_NUM = SEED_NUM_COLS * SEED_NUM_ROWS;

    @Override
    public Image process(Image originalImage) {
        int size = originalImage.getHeight();
        Image imageWithRegions = new Image(originalImage.getName(), size, size);
        Image resultImage = new Image(originalImage);

        PixelPoint[] seedPoints = prepareSeedPoints(originalImage, size);

        runRegionGrowing(originalImage, imageWithRegions, seedPoints);
        applyMaskToResultImage(size, imageWithRegions, resultImage);

        return resultImage;
    }

    private PixelPoint[] prepareSeedPoints(Image originalImage, int size) {
        PixelPoint[] seedPoints = new PixelPoint[SEED_NUM];
        for(int x=0; x< SEED_NUM_ROWS; x++){
            for(int y=0; y< SEED_NUM_COLS; y++){
                seedPoints[x * SEED_NUM_ROWS + y] = new PixelPoint(originalImage.getPixel(x*(size/SEED_NUM_ROWS), y*(size/SEED_NUM_COLS)), x*(size/SEED_NUM_ROWS), y*(size/SEED_NUM_COLS));
            }
        }
        return seedPoints;
    }

    private void runRegionGrowing(Image originalImage, Image imageWithRegions, PixelPoint[] seedPoints) {
        int size = originalImage.getHeight();
        List<List<PixelPoint>> regions = new ArrayList<>();

        for(PixelPoint seed : seedPoints){
            Stack<PixelPoint> stack = new Stack<>();
            List<PixelPoint> region = new ArrayList<>();

            if(imageWithRegions.getPixel(seed.x,seed.y).getGrayScale() == -10) {
                stack.push(seed);

                while (!stack.isEmpty()) {
                    PixelPoint cur = stack.pop();
                    for (int x = -1; x < 2; x++) {
                        for (int y = -1; y < 2; y++) {
                            if (cur.x + x >= size || cur.x + x <= 0 || cur.y + y >= size || cur.y + y <= 0) {
                                continue;
                            }
                            if (imageWithRegions.getPixel(cur.x + x, cur.y + y).getGrayScale() == 255) {
                                continue;
                            }
                            PixelPoint examinedPixelPoint = new PixelPoint(imageWithRegions.getPixel(cur.x + x, cur.y + y), cur.x + x, cur.y + y);
                            if (cur.pixel.getGrayScale() - originalImage.getPixel(cur.x + x, cur.y + y).getGrayScale() <= THRESHOLD) {
                                for (RgbColor color : RgbColor.values()){
                                    imageWithRegions.getPixel(cur.x + x, cur.y + y).setColor(color, 255);
                                }
                                region.add(examinedPixelPoint);
                                stack.push(examinedPixelPoint);
                            }
                        }
                    }
                }

                if(region.size() > MINIMUM_PIXELS_FOR_REGION){
                    regions.add(region);
                }

                if(region.size() < MINIMUM_PIXELS_FOR_REGION){
                    for(PixelPoint point : region){
                        for (Pixel.RgbColor color : Pixel.RgbColor.values()) {
                            imageWithRegions.getPixel(point.x, point.y).setColor(color, -10);
                        }
                    }
                }
                region.clear();
            }
        }

    }

    private void applyMaskToResultImage(int size, Image imageWithRegions, Image resultImage) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if(imageWithRegions.getPixel(x,y).getGrayScale() == 255){
                    resultImage.getPixel(x,y).setRed(0);
                    resultImage.getPixel(x,y).setGreen(255);
                    resultImage.getPixel(x,y).setBlue(255);
                }
            }
        }
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
