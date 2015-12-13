package main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation.tree.QuadTree;
import main.java.pl.lodz.p.ftims.poid.utils.ImageUtil;

import java.util.Stack;

/**
 * @author alisowsk
 */
public class RegionSplittingAndMerging implements Transformable {
    private static final int THRESHOLD = 100;
    private static final int SEED_X = 64;
    private static final int SEED_Y = 64;

    @Override
    public Image process(Image originalImage) {
        int size = originalImage.getHeight();
        Image resultImage = new Image(originalImage);
        Image processedImage = new Image(originalImage);

        main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation.tree.PixelPoint[][] pixelPoints = new main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation.tree.PixelPoint[size][size];

        for (Pixel.RgbColor color : Pixel.RgbColor.values()){
            for(int x=0; x<size; x++){
                for(int y=0; y<size; y++){
                    resultImage.getPixel(x, y).setColor(color, ImageUtil.getGrayScaleValue(resultImage.getPixel(x, y)));
                    pixelPoints[x][y] = new main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation.tree.PixelPoint(ImageUtil.getGrayScaleValue(resultImage.getPixel(x, y)),x,y);
                }
            }
        }


        QuadTree tree = new QuadTree(pixelPoints);
        tree.process(THRESHOLD);

//        int[][] processedPixels = new int[size][size];
//        for(int x=0; x<size; x++){
//            for(int y=0; y<size; y++){
//                processedPixels[x][y] = resultImage.getPixel(x,y).getGrayScale();
//            }
//        }
//
////        runSplitting(processedPixels);
//
//        for (Pixel.RgbColor color : Pixel.RgbColor.values()){
//            for(int x=0; x<size; x++){
//                for(int y=0; y<size; y++){
//                    resultImage.getPixel(x, y).setColor(color, 0);
//                }
//            }
//        }
//
//        runRegionSplittingAndMerging(originalImage, resultImage);

        return resultImage;
    }

//    private void runSplitting(int[][] processedPixels) {
//        QuadTree tree = new QuadTree(processedPixels);
//        tree.process();
////        tree.getRegionsBiggerThan(THRESHOLD);
//
//        int length = processedPixels.length;
////
////        int curMin = processedPixels
////        for(int x=0; x<length; x++){
////            for (int y=0; y<length; y++){
////                if()
////            }
////        }
//
//    }

    private void runRegionSplittingAndMerging(Image originalImage, Image resultImage) {


//        int size = originalImage.getHeight();
//        Stack<PixelPoint> stack = new Stack<>();
//        PixelPoint pixelPoint = new PixelPoint(originalImage.getPixel(SEED_X, SEED_Y), SEED_X, SEED_Y);
//
//        stack.push(pixelPoint);
//
//        while(!stack.isEmpty()){
//            PixelPoint currentPixelPoint = stack.pop();
//            for(int x=-1; x<2; x++){
//                for(int y=-1; y<2; y++){
//                    if(currentPixelPoint.x+x >= size || currentPixelPoint.x+x <= 0 || currentPixelPoint.y+y >= size || currentPixelPoint.y+y <= 0){
//                        continue;
//                    }
//                    if(resultImage.getPixel(currentPixelPoint.x+x,currentPixelPoint.y+y).getBlue() == 255){
//                        continue;
//                    }
//                    if(Math.abs(currentPixelPoint.pixel.getBlue() - originalImage.getPixel(currentPixelPoint.x + x, currentPixelPoint.x + y).getBlue()) <= THRESHOLD){
//                        for (Pixel.RgbColor color : Pixel.RgbColor.values()){
//                            resultImage.getPixel(currentPixelPoint.x+x,currentPixelPoint.y+y).setColor(color, 255);
//                        }
//                        stack.push(new PixelPoint(resultImage.getPixel(currentPixelPoint.x+x,currentPixelPoint.y+y),currentPixelPoint.x+x,currentPixelPoint.y+y ));
//                    }
//                }
//            }
//        }
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
