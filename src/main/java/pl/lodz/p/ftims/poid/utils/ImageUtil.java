package main.java.pl.lodz.p.ftims.poid.utils;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;

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
        return new Image(pixels, img.getWidth(), img.getHeight());
    }

    public static Image convertBufferedImageToImage(BufferedImage bufferedImage){
        //TODO
        return null;
    }

    public static BufferedImage convertImageToBufferedImage(Image image){
        final int width = image.getWidth();
        final int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB );
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                result.setRGB(x, y, ImageUtil.getRgbValue(image.getPixels()[x][y]));
            }
        }
        return result;
    }

    public static int getRgbValue(Pixel pixel){
        return pixel.getRed() << 16 | pixel.getGreen() << 8 | pixel.getBlue();
    }
}
