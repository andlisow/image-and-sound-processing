package main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by <a href="mailto:171131@edu.p.lodz.pl">Andrzej Lisowski</a> on 13.12.15.
 */
public class QuadNode {
    private static final Logger LOG = LoggerFactory.getLogger(QuadNode.class);

    PixelPoint[][] values;

    boolean visited;
    boolean hasChildren = false;
    private QuadNode NW = null;
    private QuadNode NE = null;
    private QuadNode SE = null;
    private QuadNode SW = null;
    private QuadNode firstLeaf;

    public QuadNode(PixelPoint[][] values) {
        this.values = values;
    }

    public void executeSplit(int threshold) {
        if(values.length == 1){
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
            split(threshold);
        } else {
            LOG.info("Similar region found");
        }
    }

    private void split(int threshold){
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

        NW = new QuadNode(valuesNW);
        NW.executeSplit(threshold);
        NE = new QuadNode(valuesNE);
        NE.executeSplit(threshold);
        SE = new QuadNode(valuesSW);
        SE.executeSplit(threshold);
        SW = new QuadNode(valuesSW);
        SW.executeSplit(threshold);
    }

    public QuadNode getFirstLeaf() {
        if(NW != null && !NW.visited){
            return NW;
        } else if(NE != null && !NE.visited){
            return NE;
        } else if(SW != null && !SW.visited){
            return SW;
        } else if(SE != null && !SE.visited){
            return SE;
        } else {
            visited = true;
            return this;
        }
    }
}