package main.java.pl.lodz.p.ftims.poid.operations;

import main.java.pl.lodz.p.ftims.poid.model.Image;
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
        LOG.error(String.valueOf(resultImage.getPixels()[1][1].getBlue()));
        for(Transformable transformable : operations){
            transformable.process(resultImage);
        }
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
