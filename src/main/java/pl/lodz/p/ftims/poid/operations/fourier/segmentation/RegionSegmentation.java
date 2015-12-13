package main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation;

import main.java.pl.lodz.p.ftims.poid.operations.Transformable;

/**
 * @author alisowsk
 */
public interface RegionSegmentation extends Transformable {

    void setThreshold(int threshold);

    void setMinimumPixelsForRegion(int minimumPixelsForRegion);
}
