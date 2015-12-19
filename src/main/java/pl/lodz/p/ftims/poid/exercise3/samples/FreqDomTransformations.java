package main.java.pl.lodz.p.ftims.poid.exercise3.samples;

import main.java.pl.lodz.p.ftims.poid.exercise3.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.exercise3.operations.freqdom.FourierSpectrumAnalysis;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author alisowsk
 */
public class FreqDomTransformations {
    public static final Map<String, Transformable> TRANSFORMATIONS = new LinkedHashMap<>();

    static{
        TRANSFORMATIONS.put("Fourier spectrum analysis", new FourierSpectrumAnalysis());
        //TODO add more methods
    }

    private FreqDomTransformations(){

    }
}
