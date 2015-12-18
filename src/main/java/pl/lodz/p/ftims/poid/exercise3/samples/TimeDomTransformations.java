package main.java.pl.lodz.p.ftims.poid.exercise3.samples;

import main.java.pl.lodz.p.ftims.poid.exercise3.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.exercise3.operations.freqdom.CepstumAnalysis;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author alisowsk
 */
public class TimeDomTransformations {
    public static final Map<String, Transformable> TRANSFORMATIONS = new LinkedHashMap<>();

    static{
        TRANSFORMATIONS.put("Cepstrum analysis", new CepstumAnalysis());
        //TODO add more methods
    }

    private TimeDomTransformations(){

    }
}
