package main.java.pl.lodz.p.ftims.poid.operations.comparison;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.utils.ImageConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alisowsk
 */
public class Psnr {
    private static final Logger LOG = LoggerFactory.getLogger(Psnr.class);

    private Image modelImage;
    private Image examinedImage;

    public Psnr (Image examinedImage, Image modelImage){
        this.modelImage = modelImage;
        this.examinedImage = examinedImage;
    }

    public double calculatePsnr(){
        Mse mse = new Mse(examinedImage,modelImage);
        double mseValue = mse.calculateMse();

        return 10 * Math.log10(Math.pow(ImageConstants.MAX_PIXEL_VALUE, 2) / mseValue);
    }
}
