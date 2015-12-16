package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.segmentation.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alisowsk
 */
public class QuadTree {
    private static final Logger LOG = LoggerFactory.getLogger(QuadNode.class);

    private QuadNode root = null;
    private List<PixelPoint[][]> regions = new ArrayList<>();

    /**
     * Creates an empty QuadTree with the bounds
     */
    public QuadTree(PixelPoint[][] values) {
        this.root = new QuadNode(values);
    }

    public void process(int threshold){
        root.executeSplit(threshold, regions);

        int sum=0;

        for(PixelPoint[][] arr : regions){
            sum+=Math.pow(arr.length,2);
        }

        LOG.info("Obtained {} regions and pixels {}", regions.size(), sum);
    }

    public List<PixelPoint[][]> getRegions(){
        return regions;
    }

}