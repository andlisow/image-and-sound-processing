package main.java.pl.lodz.p.ftims.poid.samples;

import main.java.pl.lodz.p.ftims.poid.operations.histogram.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author alisowsk
 */
public class HistogramModification {
    public static final Map<String, AbstractFinalProbDensFunction> VARIANTS = new LinkedHashMap<>();
    static{
        VARIANTS.put("", null);
        VARIANTS.put("Uniform final probability density function", new UniformFinalProbDensFunction());
        VARIANTS.put("Exponential final probability density function", new ExponentialFinalProbDensFunction());
        VARIANTS.put("Raleigh final probability density function", new RaleighFinalProbDensFunction());
        VARIANTS.put("Power 2/3 final probability density function", new PowerTwoThirdsFinalProbDensFunction());
        VARIANTS.put("Hyperbolic final probability density function", new HyperbolicFinalProbDensFunction());
    }

    private HistogramModification(){

    }
}
