package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alisowsk
 */
public class Operations {
    private List<Transformable> operations = new ArrayList<>();
    private Image resultImage;

    public Operations(){

    }

    public Image processImage(Image originImage){
        resultImage = new Image(originImage);
        for(Transformable transformable : operations){
            resultImage = transformable.process(resultImage);
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

    public List<Transformable> getOperations() {
        return operations;
    }
}
