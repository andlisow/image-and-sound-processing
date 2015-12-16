package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.comparison.Mae;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.comparison.Mse;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.comparison.Psnr;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.basic.MeanFilter;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.basic.MedianFilter;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author alisowsk
 */
public abstract class AbstractFilter implements Transformable {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractFilter.class);

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
        logFilterComparison(originalImage, result);
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

    //TODO move the method to another place
    //TODO user should have a possibility to open any model file
    private void logFilterComparison(Image originalImage, Image resultImage) {
        if(this instanceof MeanFilter || this instanceof MedianFilter){
            Image modelImage = null;
            try {
                if (originalImage.getName().contains("lenac")) {
                    modelImage = ImageUtil.readImageFromFile(new File("/home/andrzej/Pulpit/POID/SRC/image-and-sound-processing/src/main/resources/exercise_1_2/bit24/lenac_small.bmp"));
                } else if (originalImage.getName().contains("lena")) {
                    modelImage = ImageUtil.readImageFromFile(new File("/home/andrzej/Pulpit/POID/SRC/image-and-sound-processing/src/main/resources/exercise_1_2/bit8/lena_small.bmp"));
                } else {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            LOG.info("MSE before change: " + new Mse(originalImage, modelImage).calculateMse());
            LOG.info("PSNR before change: " + new Psnr(originalImage, modelImage).calculatePsnr());
            LOG.info("MAE before change: " + new Mae(originalImage, modelImage).calculateMae());

            LOG.info("MSE after change: " + new Mse(resultImage, modelImage).calculateMse());
            LOG.info("PSNR after change: " + new Psnr(resultImage, modelImage).calculatePsnr());
            LOG.info("MAE after change: " + new Mae(resultImage, modelImage).calculateMae());
        }
    }
}
