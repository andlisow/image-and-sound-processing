package main.java.pl.lodz.p.ftims.poid.operations.comparison;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alisowsk
 */
public class Mae {
    private static final Logger LOG = LoggerFactory.getLogger(Mae.class);

    private Image modelImage;
    private Image examinedImage;

    public Mae(Image examinedImage, Image modelImage){
        this.modelImage = modelImage;
        this.examinedImage = examinedImage;
    }

    public double calculateMae(){
        double sum = 0;
        for(int x=0; x<examinedImage.getWidth(); x++){
            for(int y=0; y<examinedImage.getWidth(); y++){
                for(RgbColor color : RgbColor.values()){
                    sum += Math.abs((examinedImage.getPixel(x,y).getColor(color)) - (modelImage.getPixel(x,y).getColor(color)));
                }
            }
        }
        return sum / (examinedImage.getHeight() * examinedImage.getWidth() * 3);
    }
}
