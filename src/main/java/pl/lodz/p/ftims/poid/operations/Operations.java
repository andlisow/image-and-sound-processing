package main.java.pl.lodz.p.ftims.poid.operations;

import com.sun.glass.ui.Pixels;
import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alisowsk
 */
public class Operations {
    private static final Logger LOG = LoggerFactory.getLogger(Operations.class);
    private List<Transformable> operations = new ArrayList<>();
    private Image resultImage;

    public Operations(){

    }

    public Image processImage(Image originImage){
        resultImage = new Image(originImage);



        for(Transformable transformable : operations){
            resultImage = transformable.process(resultImage);
        }

//
//        for(int x=0; x<originImage.getWidth(); x++){
//            for( int y=0; y<originImage.getHeight(); y++){
//                LOG.error(originImage.getPixels()[x][y].getBlue() + " : " + resultImage.getPixels()[x][y].getBlue());
//                LOG.error(originImage.getPixels()[x][y].getGreen() + " : " + resultImage.getPixels()[x][y].getGreen());
//                LOG.error(originImage.getPixels()[x][y].getRed() + " : " + resultImage.getPixels()[x][y].getRed());
//            }
//        }
        return resultImage;
    }

    public void clear() {
        operations.clear();
    }

    public void addOperation(Transformable transformable){
        operations.add(transformable);
    }

    public void removeOperation(Transformable transformable){
        operations.remove(transformable);
    }
}
