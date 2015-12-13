package main.java.pl.lodz.p.ftims.poid.utils;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author alisowsk
 */
public class ImageUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ImageUtil.class);

    //TODO move to program args?
    //TODO configuration file on start of the program
    private static final String DEFAULT_FILE_SAVE_PATH = "/home/andrzej/poid/";
    private static final String DEFAULT_FILE_EXTENSION = "png";

    public static Image readImageFromFile(File imgFile) throws IOException {
        String imgName = imgFile.getName();
        //TODO check if file is proper image, if not throw ex
        //TODO custom ex
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
        return new Image(imgName, pixels, img.getRaster().getNumDataElements());
    }

    public static void saveImageToFile(Image image) throws IOException {
        //TODO logger
        File imgFile = new File(DEFAULT_FILE_SAVE_PATH + image.getName().split("\\.")[0] + "." + DEFAULT_FILE_EXTENSION);
        BufferedImage bufferedImage = convertImageToBufferedImage(image);
        ImageIO.write(bufferedImage, DEFAULT_FILE_EXTENSION, imgFile);
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
                int rgbValue = getRgbValue(image.getPixels()[x][y]);
                result.setRGB(x, y, rgbValue);
            }
        }
        return result;
    }

    public static int getRgbValue(Pixel pixel){
        return pixel.getRed() << 16 | pixel.getGreen() << 8 | pixel.getBlue();
    }

    public static int getGrayScaleValue(Pixel pixel){
        return (int) (0.299 * pixel.getRed()  + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
    }
}
