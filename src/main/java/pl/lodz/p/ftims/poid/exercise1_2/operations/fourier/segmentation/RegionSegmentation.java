package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.segmentation;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.Transformable;

/**
 * @author alisowsk
 */
public interface RegionSegmentation extends Transformable {

    void setThreshold(int threshold);

    void setMinimumPixelsForRegion(int minimumPixelsForRegion);
}
