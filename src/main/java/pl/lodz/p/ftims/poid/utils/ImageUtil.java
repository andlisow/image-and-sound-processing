package main.java.pl.lodz.p.ftims.poid.utils;

import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.model.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author alisowsk
 */
public class ImageUtil {

    public static Image readImageFromFile(String filePath) throws IOException {
        File imgFile = new File(filePath);
        //TODO check if file is proper image, if not throw ex
        BufferedImage img = ImageIO.read(imgFile);
        Pixel[][] pixels = new Pixel[img.getWidth()][img.getHeight()];
        for(int x=0; x< img.getWidth(); x++){
            for(int y=0; y<img.getHeight(); y++){
                int redValue = new Color(img.getRGB(x,y)).getRed();
                int greenValue = new Color(img.getRGB(x,y)).getGreen();
                int blueValue = new Color(img.getRGB(x,y)).getBlue();
                pixels[x][y] = new Pixel(redValue, greenValue, blueValue);
            }
        }
        return new Image(pixels);
    }
}
