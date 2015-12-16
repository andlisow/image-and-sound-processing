package main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.filters.nonlinear;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.nonlinear.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author alisowsk
 */
public class NonLinearFilters {
    public static final Map<String, Transformable> FILTERS = new LinkedHashMap<>();
    static{
        FILTERS.put("Roberts Operator 1", new RobertsOperator1());
        FILTERS.put("Roberts Operator 2", new RobertsOperator2());
        FILTERS.put("Sobel Operator", new SobelOperator());
        FILTERS.put("Kirsh Operator", new KirshOperator());
        FILTERS.put("Rosenfeld Operator", new RosenfeldOperator());
        FILTERS.put("Uolis Operator", new UolisOperator());
    }

    private NonLinearFilters(){

    }
}
