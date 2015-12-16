package main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.segmentation;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.segmentation.RegionGrowing;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.segmentation.RegionSegmentation;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.segmentation.RegionSplittingAndMerging;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author alisowsk
 */
public class Segmentation {
    public static final Map<String, RegionSegmentation> SEGMENTATIONS = new LinkedHashMap<>();
    static{
        SEGMENTATIONS.put("Region growing", new RegionGrowing());
        SEGMENTATIONS.put("Region split. and merg.", new RegionSplittingAndMerging());
    }

    private Segmentation(){

    }
}
