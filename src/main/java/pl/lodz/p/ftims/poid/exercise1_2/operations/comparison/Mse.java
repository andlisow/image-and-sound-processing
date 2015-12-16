package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.comparison;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Pixel.RgbColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alisowsk
 */
public class Mse {
    private static final Logger LOG = LoggerFactory.getLogger(Mse.class);

    private Image modelImage;
    private Image examinedImage;

    public Mse (Image examinedImage, Image modelImage){
        this.modelImage = modelImage;
        this.examinedImage = examinedImage;
    }

    public double calculateMse(){
        double sum = 0;
        for(int x=0; x<examinedImage.getWidth(); x++){
            for(int y=0; y<examinedImage.getWidth(); y++){
                for(RgbColor color : RgbColor.values()){
                    sum += Math.pow((examinedImage.getPixel(x,y).getColor(color)) - (modelImage.getPixel(x,y).getColor(color)),2);
                }
            }
        }
        return sum / (examinedImage.getHeight() * examinedImage.getWidth() * 3);
    }
}
