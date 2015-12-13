package main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author alisowsk
 */
public class QuadNode {
    private static final Logger LOG = LoggerFactory.getLogger(QuadNode.class);

    PixelPoint[][] values;

    public int maxX;
    public int maxY;
    public int minX;
    public int minY;

    boolean hasChildren = false;
    private QuadNode NW = null;
    private QuadNode NE = null;
    private QuadNode SE = null;
    private QuadNode SW = null;

    public QuadNode(PixelPoint[][] values, int minX, int maxX, int minY, int maxY) {
        this.values = values;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public void executeSplit(int threshold, List<PixelPoint[][]> leavesValues) {
        if(values.length == 1){
            leavesValues.add(values);
            return;
        }

        int curMin = values[0][0].getValue();
        int curMax = values[0][0].getValue();

        for(int i=0; i<values.length; i++){
            for(int j=0; j<values[0].length; j++){
                int curVal = values[i][j].getValue();
                if(curMin > curVal){
                    curMin = curVal;
                }
                if(curMax < curVal){
                    curMax = curVal;
                }
            }
        }

        if((curMax - curMin) > threshold){
            hasChildren = true;
            split(threshold, leavesValues);
        } else {
            leavesValues.add(values);
            LOG.info("Similar region found");
        }
    }

    private void split(int threshold, List<PixelPoint[][]> leavesValues){
        LOG.error("Length of splitting region: " + values.length);

        int quadSize = values.length/2;

        PixelPoint[][] valuesNW = new PixelPoint[quadSize][quadSize];
        PixelPoint[][] valuesNE = new PixelPoint[quadSize][quadSize];
        PixelPoint[][] valuesSE = new PixelPoint[quadSize][quadSize];
        PixelPoint[][] valuesSW = new PixelPoint[quadSize][quadSize];

        for(int i=0; i<values.length; i++){
            for(int j=0; j<values[0].length; j++){
                if(i<quadSize && j<quadSize){
                    valuesNW[i][j] = new PixelPoint(values[i][j].getValue(), values[i][j].getX(), values[i][j].getY());
                } else if(i>=quadSize && j<quadSize){
                    valuesNE[i-quadSize][j] = new PixelPoint(values[i][j].getValue(), values[i][j].getX(), values[i][j].getY());
                } else if(i<quadSize && j>=quadSize){
                    valuesSE[i][j-quadSize] = new PixelPoint(values[i][j].getValue(), values[i][j].getX(), values[i][j].getY());
                } else {
                    valuesSW[i-quadSize][j-quadSize] = new PixelPoint(values[i][j].getValue(), values[i][j].getX(), values[i][j].getY());
                }
            }
        }

        NW = new QuadNode(valuesNW, minX, maxX/2, minY, minY/2);
        NW.executeSplit(threshold, leavesValues);
        NE = new QuadNode(valuesNE, maxX/2, maxX, minY, minY/2);
        NE.executeSplit(threshold, leavesValues);
        SE = new QuadNode(valuesSE, maxX/2, maxX, minY/2, minY);
        SE.executeSplit(threshold, leavesValues);
        SW = new QuadNode(valuesSW, maxX, maxX/2, minY/2, minY);
        SW.executeSplit(threshold, leavesValues);
    }
}