package main.java.pl.lodz.p.ftims.poid.samples.fourier;

import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.operations.filters.nonlinear.*;
import main.java.pl.lodz.p.ftims.poid.operations.fourier.filters.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author alisowsk
 */
public class FourierFilters {
    public static final Map<String, Transformable> FILTERS = new LinkedHashMap<>();
    static{
        FILTERS.put("Low-pass", new LowPassFilter());
        FILTERS.put("High-pass", new HighPassFilter());
        FILTERS.put("Band-pass", new BandPassFilter());
        FILTERS.put("Band-cut", new BandCutFilter());
        FILTERS.put("High-pass det. edge dir.", new HighPassDetectionEdgeDirectionFilter());
    }

    private FourierFilters(){

    }
}
