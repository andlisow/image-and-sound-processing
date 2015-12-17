package main.java.pl.lodz.p.ftims.poid.exercise3.operations;

import main.java.pl.lodz.p.ftims.poid.exercise3.model.WavFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alisowsk
 */
public class Operations {
    private List<Transformable> operations = new ArrayList<>();
    private WavFile resultSound;

    public Operations(){

    }

    public WavFile processSound(WavFile originSound){
        resultSound = new WavFile(originSound);
        for(Transformable transformable : operations){
            resultSound = transformable.process(resultSound);
        }
        return resultSound;
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